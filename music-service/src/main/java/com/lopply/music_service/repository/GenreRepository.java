package com.lopply.music_service.repository;

import com.lopply.music_service.entity.Genre;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends ReactiveCrudRepository<Genre,Long> {
}
