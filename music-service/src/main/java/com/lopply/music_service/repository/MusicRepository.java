package com.lopply.music_service.repository;

import com.lopply.music_service.entity.Music;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface MusicRepository extends ReactiveCrudRepository<Music, Long> {

    Mono<Music> findByUuid(UUID id);

    @Query("SELECT * FROM music " +
            "JOIN artist ON artist.id = music.artist_id " +
            "WHERE music.id = :musicId")
    Mono<Music> findByMusicWithArtist(@Param("musicId") Long musicId);

    @Query("Select * from music\n" +
            "join artist on artist.id = music.artist_id\n" +
            "where artist_id = :artistId")
    Flux<Music> findAllArtistMusic(Long artistId);
}
