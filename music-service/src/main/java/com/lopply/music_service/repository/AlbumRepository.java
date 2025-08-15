package com.lopply.music_service.repository;

import com.lopply.music_service.entity.Album;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AlbumRepository extends ReactiveCrudRepository<Album,Long> {

    Mono<Album> findByUuid(String uuid);
}
