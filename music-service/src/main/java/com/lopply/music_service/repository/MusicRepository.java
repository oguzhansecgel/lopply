package com.lopply.music_service.repository;

import com.lopply.music_service.entity.Music;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository extends ReactiveCrudRepository<Music, Long> {
}
