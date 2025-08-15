package com.lopply.music_service.repository;

import com.lopply.music_service.entity.Artist;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ArtistRepository extends ReactiveCrudRepository<Artist,Long> {

    Mono<Artist> findByUuid(String uuid);
}
