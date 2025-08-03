package com.loplyy.subscriber_service.repository;

import com.loplyy.subscriber_service.entity.SubscriberPlaylist;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriberPlaylistRepository extends ReactiveCrudRepository<SubscriberPlaylist,Long> {
}
