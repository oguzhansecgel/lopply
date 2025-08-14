package com.lopply.music_service.service;

import com.lopply.music_service.dto.request.music.CreateMusicRequest;
import com.lopply.music_service.entity.Music;
import com.lopply.music_service.repository.MusicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MusicServiceImpl {

    private final MusicRepository musicRepository;
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    public MusicServiceImpl(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    public Mono<Void> createMusic(CreateMusicRequest request) {
        logger.info("Incoming create music request {}", request);
        Music music = new Music();
        music.setTitle(request.getTitle());
        music.setAlbumId(request.getAlbumId());
        music.setArtistId(request.getArtistId());
        music.setGenreId(request.getGenreId());
        music.setDuration(request.getDuration());
        music.setLanguage(request.getLanguage());

        return musicRepository.save(music)
                .doOnNext(savedMusic -> {logger.info("music saved {}", savedMusic);})
                .then();
    }
}
