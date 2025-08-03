package com.loplyy.subscriber_service.service;

import com.loplyy.subscriber_service.dto.response.playlist.GetSubscriberPlaylistResponse;
import com.loplyy.subscriber_service.entity.SubscriberPlaylist;
import com.loplyy.subscriber_service.repository.SubscriberPlaylistRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SubscriberPlaylistServiceImpl {

    private final SubscriberPlaylistRepository subscriberPlaylistRepository;

    public SubscriberPlaylistServiceImpl(SubscriberPlaylistRepository subscriberPlaylistRepository) {
        this.subscriberPlaylistRepository = subscriberPlaylistRepository;
    }

    public Flux<GetSubscriberPlaylistResponse> getAllSubscriberPlaylists() {
        return subscriberPlaylistRepository.findAll()
                .map( subscriberPlaylist -> new GetSubscriberPlaylistResponse(
                        subscriberPlaylist.getId(),
                        subscriberPlaylist.getUuid(),
                        subscriberPlaylist.getMusicId(),
                        subscriberPlaylist.getSubscriberId(),
                        subscriberPlaylist.isPublic()
                ));
    }
}
