package com.loplyy.subscriber_service.service;

import com.loplyy.subscriber_service.client.Auth.AuthServiceClient;
import com.loplyy.subscriber_service.client.Auth.GetSubscriberIdByUid;
import com.loplyy.subscriber_service.dto.request.playlist.CreatePlaylistRequest;
import com.loplyy.subscriber_service.dto.response.playlist.GetPlaylistWithItem;
import com.loplyy.subscriber_service.dto.response.playlist.GetSubscriberPlaylistResponse;
import com.loplyy.subscriber_service.dto.response.playlistItem.GetPlaylistItem;
import com.loplyy.subscriber_service.entity.SubscriberPlaylist;
import com.loplyy.subscriber_service.repository.SubscriberPlaylistItemRepository;
import com.loplyy.subscriber_service.repository.SubscriberPlaylistRepository;
import com.loplyy.subscriber_service.repository.SubscriberRepository;
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

    public SubscriberPlaylistServiceImpl(SubscriberPlaylistRepository subscriberPlaylistRepository, SubscriberPlaylistItemRepository subscriberPlaylistItemRepository, SubscriberRepository subscriberRepository, AuthServiceClient authServiceClient) {
        this.subscriberPlaylistRepository = subscriberPlaylistRepository;
        this.subscriberPlaylistItemRepository = subscriberPlaylistItemRepository;
        this.subscriberRepository = subscriberRepository;
        this.authServiceClient = authServiceClient;
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
}