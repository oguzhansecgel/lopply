package com.loplyy.subscriber_service.repository;

import com.loplyy.subscriber_service.dto.request.playlist.CreatePlaylistRequest;
import com.loplyy.subscriber_service.dto.response.playlist.GetPlaylistWithItem;
import com.loplyy.subscriber_service.entity.SubscriberPlaylist;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface SubscriberPlaylistRepository extends ReactiveCrudRepository<SubscriberPlaylist,Long> {

    Mono<SubscriberPlaylist> getPlaylistIdByUuid(UUID uuid);
    Mono<SubscriberPlaylist> save(CreatePlaylistRequest  request);
}
