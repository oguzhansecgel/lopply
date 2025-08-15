package com.lopply.music_service.service;

import com.lopply.music_service.dto.request.artist.CreateArtistRequest;
import com.lopply.music_service.entity.Artist;
import com.lopply.music_service.repository.ArtistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ArtistServiceImpl {

    private final ArtistRepository artistRepository;
    private Logger logger = LoggerFactory.getLogger(ArtistServiceImpl.class);

    public ArtistServiceImpl(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public Mono<Artist> getArtistByUId(String UId) {
        return artistRepository.findByUuid(UId)
                .switchIfEmpty(Mono.defer(()->{
                    logger.warn("Artist Not Found");
                    return Mono.error(new RuntimeException("Artist Not Found For UId " + UId));
                }));
    }

    public Mono<Void> createArtist(CreateArtistRequest request) {
        Artist artist = new Artist();
        artist.setBio(request.getBio());
        artist.setName(request.getName());
        artist.setCountry(request.getCountry());
        return artistRepository.save(artist)
                .doOnNext(savedArtist -> logger.info("Artist Create {}",savedArtist))
                .then();
    }
}
