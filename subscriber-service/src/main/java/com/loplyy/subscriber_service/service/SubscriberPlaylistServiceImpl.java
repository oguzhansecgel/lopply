package com.loplyy.subscriber_service.service;

import com.loplyy.subscriber_service.client.Auth.AuthServiceClient;
import com.loplyy.subscriber_service.client.Auth.GetSubscriberIdByUid;
import com.loplyy.subscriber_service.client.Music.MusicServiceClient;
import com.loplyy.subscriber_service.dto.request.playlist.CreatePlaylistItemRequest;
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
    private final SubscriberRepository  subscriberRepository;
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
                .map( subscriberPlaylist -> new GetSubscriberPlaylistResponse(
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
                .next()
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

    public Mono<Void> addPlaylistItem(CreatePlaylistItemRequest request) {
        logger.info("addPlaylistItem incoming request {}, {}", request.getPlaylist_uid(), request.getMusic_uid());

        Musicservice.MusicById musicById = musicServiceClient.getMusicById(request.getMusic_uid());

        return subscriberPlaylistRepository.getPlaylistIdByUuid(UUID.fromString(request.getPlaylist_uid()))
                .next()
                .switchIfEmpty(Mono.error(new RuntimeException("Playlist not found")))
                .flatMap(playlistId -> {
                    SubscriberPlaylistItem item = new SubscriberPlaylistItem();
                    item.setPlaylistId(playlistId.getId());
                    item.setMusicId(musicById.getId());
                    return subscriberPlaylistItemRepository.save(item);
                })
                .doOnSuccess(v -> logger.info("Playlist item successfully added"))
                .doOnError(e -> logger.error("Failed to add playlist item", e))
                .then();
    }
}