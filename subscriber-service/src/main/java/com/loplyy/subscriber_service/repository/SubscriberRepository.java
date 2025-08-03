package com.loplyy.subscriber_service.repository;

import com.loplyy.subscriber_service.entity.Subscriber;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface SubscriberRepository extends ReactiveCrudRepository<Subscriber, Long> {

    Mono<Subscriber> getSubscriberIdByUuid(String uuid);
}
