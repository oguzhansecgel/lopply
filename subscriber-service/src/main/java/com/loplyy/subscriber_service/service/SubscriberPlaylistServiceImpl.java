package com.loplyy.subscriber_service.service;

import com.loplyy.subscriber_service.client.Auth.AuthServiceClient;
import com.loplyy.subscriber_service.client.Music.MusicServiceClient;
import com.loplyy.subscriber_service.dto.request.playlist.PlaylistItemRequest;
import com.loplyy.subscriber_service.dto.request.playlist.CreatePlaylistRequest;
import com.loplyy.subscriber_service.dto.response.playlist.GetPlaylistWithItem;
import com.loplyy.subscriber_service.dto.response.playlist.GetSubscriberPlaylistResponse;
import com.loplyy.subscriber_service.dto.response.playlistItem.GetPlaylistItem;
import com.loplyy.subscriber_service.entity.SubscriberPlaylist;
import com.loplyy.subscriber_service.entity.SubscriberPlaylistItem;
import com.loplyy.subscriber_service.repository.SubscriberPlaylistItemRepository;
import com.loplyy.subscriber_service.repository.SubscriberPlaylistRepository;
import com.loplyy.subscriber_service.repository.SubscriberRepository;
import com.lopply.music.Musicservice;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class SubscriberPlaylistServiceImpl {

    private final SubscriberPlaylistRepository subscriberPlaylistRepository;
    private final SubscriberPlaylistItemRepository subscriberPlaylistItemRepository;
    private final SubscriberRepository subscriberRepository;
    private final AuthServiceClient authServiceClient;
    private final MusicServiceClient musicServiceClient;
    private Logger logger = LoggerFactory.getLogger(SubscriberPlaylistServiceImpl.class);

    public SubscriberPlaylistServiceImpl(SubscriberPlaylistRepository subscriberPlaylistRepository,
                                         SubscriberPlaylistItemRepository subscriberPlaylistItemRepository,
                                         SubscriberRepository subscriberRepository,
                                         AuthServiceClient authServiceClient,
                                         MusicServiceClient musicServiceClient) {
        this.subscriberPlaylistRepository = subscriberPlaylistRepository;
        this.subscriberPlaylistItemRepository = subscriberPlaylistItemRepository;
        this.subscriberRepository = subscriberRepository;
        this.authServiceClient = authServiceClient;
        this.musicServiceClient = musicServiceClient;
    }

    public Flux<GetSubscriberPlaylistResponse> getAllSubscriberPlaylists() {
        return subscriberPlaylistRepository.findAll()
                .map(subscriberPlaylist -> new GetSubscriberPlaylistResponse(
                        subscriberPlaylist.getId(),
                        subscriberPlaylist.getUuid(),
                        subscriberPlaylist.getSubscriberId(),
                        subscriberPlaylist.isPublic()
                ));
    }

    public Mono<Void> deleteSubscriberPlaylist(String uuid) {
        return subscriberPlaylistRepository.getPlaylistIdByUuid(UUID.fromString(uuid))
                .flatMap(playlistId -> {
                    return subscriberPlaylistRepository.deleteById(playlistId.getId());
                }).then();
    }

    public Mono<GetPlaylistWithItem> getPlaylistWithItem(String playlistUId) {
        return subscriberPlaylistRepository.getPlaylistIdByUuid(UUID.fromString(playlistUId))
                .flatMap(playlist ->
                        subscriberPlaylistItemRepository.findByPlaylistId(playlist.getId())
                                .map(item -> new GetPlaylistItem(
                                        item.getId(),
                                        item.getMusic_uid(),
                                        item.getPlaylist_id(),
                                        item.getTitle(),
                                        item.getAuthor()
                                ))
                                .collectList()
                                .map(itemList -> new GetPlaylistWithItem(
                                        playlist.getId(),
                                        playlist.getUuid(),
                                        playlist.getSubscriberId(),
                                        playlist.isPublic(),
                                        itemList
                                ))
                );
    }

    public Mono<Void> createSubscriberPlaylist(CreatePlaylistRequest request, String accountUId) {
        logger.info("createSubscriberPlaylist incoming request {}", request.toString());

        return authServiceClient.getUserIdByUid(UUID.fromString(accountUId))
                .flatMap(accountIdDto ->
                        subscriberRepository.getSubscriberByAccountId(accountIdDto.getId())
                                .flatMap(subscriber -> {
                                    request.setSubscriberId(subscriber.getId());
                                    SubscriberPlaylist playlist = new SubscriberPlaylist(
                                            request.isPublic(),
                                            request.getSubscriberId()
                                    );
                                    return subscriberPlaylistRepository.save(playlist);
                                })
                )
                .then();
    }

    public Mono<Void> addPlaylistItem(PlaylistItemRequest request) {
        logger.info("addPlaylistItem incoming request {}, {}", request.getPlaylist_uid(), request.getMusic_uid());

        Mono<Long> musicIdMono = musicServiceClient.getMusicById(request.getMusic_uid())
                .switchIfEmpty(Mono.error(new StatusRuntimeException(Status.NOT_FOUND.withDescription("Music not found"))))
                .map(Musicservice.MusicById::getId);

        Mono<Long> playlistIdMono = subscriberPlaylistRepository.getPlaylistIdByUuid(UUID.fromString(request.getPlaylist_uid()))
                .switchIfEmpty(Mono.error(new StatusRuntimeException(Status.NOT_FOUND.withDescription("Playlist not found"))))
                .map(SubscriberPlaylist::getId);

        return Mono.zip(musicIdMono, playlistIdMono)
                .flatMap(tuple -> {
                    Long musicId = tuple.getT1();
                    Long playlistId = tuple.getT2();
                    SubscriberPlaylistItem item = new SubscriberPlaylistItem();
                    item.setPlaylistId(playlistId);
                    item.setMusicId(musicId);
                    return subscriberPlaylistItemRepository.save(item);
                })
                .then();
    }

    public Mono<Void> removePlaylistItem(String musicUId, String playlistUId) {
        logger.info("removePlaylistItem incoming request musicUId: {}, playlistUId: {}", musicUId, playlistUId);
        Mono<Long> musicIdMono = musicServiceClient.getMusicById(musicUId)
                .switchIfEmpty(Mono.error(new StatusRuntimeException(Status.NOT_FOUND.withDescription("Music not found"))))
                .map(Musicservice.MusicById::getId);
        Mono<Long> playlistIdMono = subscriberPlaylistRepository.getPlaylistIdByUuid(UUID.fromString(playlistUId))
                .switchIfEmpty(Mono.error(new StatusRuntimeException(Status.NOT_FOUND.withDescription("Playlist not found"))))
                .map(SubscriberPlaylist::getId);

        return Mono.zip(musicIdMono, playlistIdMono)
                .flatMap(tuple -> {
                    Long musicId = tuple.getT1();
                    Long playlistId = tuple.getT2();
                    return subscriberPlaylistItemRepository.findIdByMusicIdAndPlaylistId(musicId, playlistId);
                })
                .switchIfEmpty(Mono.error(new StatusRuntimeException(Status.NOT_FOUND.withDescription("Playlist item not found for given music & playlist"))))
                .flatMap(subscriberPlaylistItemRepository::deleteById);
    }
}