package com.loplyy.subscriber_service.repository;

import com.loplyy.subscriber_service.dto.response.playlist.GetPlaylistWithItem;
import com.loplyy.subscriber_service.dto.response.playlistItem.GetPlaylistItem;
import com.loplyy.subscriber_service.entity.SubscriberPlaylistItem;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface SubscriberPlaylistItemRepository extends ReactiveCrudRepository<SubscriberPlaylistItem, Long> {

    Flux<GetPlaylistItem> findByPlaylistId(Long playlistId);

    @Query("""
            SELECT sp.id
            FROM subscriber_playlistitem sp
            WHERE sp.music_id = :musicId AND sp.playlist_id = :playlistId
            """)
    Mono<Long> findIdByMusicIdAndPlaylistId(@Param("musicId") Long musicId,
                                            @Param("playlistId") Long playlistId);

}
