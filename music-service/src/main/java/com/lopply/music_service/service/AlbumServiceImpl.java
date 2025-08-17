package com.lopply.music_service.service;

import com.lopply.music_service.dto.request.album.CreateAlbumRequest;
import com.lopply.music_service.entity.Album;
import com.lopply.music_service.repository.AlbumRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AlbumServiceImpl {

    private final AlbumRepository albumRepository;
    private Logger logger = LoggerFactory.getLogger(AlbumServiceImpl.class);

    public AlbumServiceImpl(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public Flux<Album> findAll() {
        return albumRepository.findAll();
    }

    public Mono<Album> getAlbumByUId(String uid) {
        return albumRepository.findByUuid(uid)
                .switchIfEmpty(Mono.defer(() -> {
                            logger.warn("Album Not Found For UId {}", uid);
                            return Mono.error(new RuntimeException("Album Not Found For UId " + uid));
                        }
                ));
    }

    public Mono<Void> createAlbum(CreateAlbumRequest request) {
        Album album = new Album();
        album.setArtistId(request.getArtistId());
        album.setTitle(request.getTitle());
        album.setReleaseDate(request.getReleaseDate());

        return albumRepository.save(album)
                .doOnNext(savedAlbum -> logger.info("saved album {}", savedAlbum))
                .then();
    }
}
