package com.lopply.music_service.service;

import com.lopply.music_service.dto.request.music.CreateMusicRequest;
import com.lopply.music_service.dto.response.music.GetByUIdMusic;
import com.lopply.music_service.entity.Music;
import com.lopply.music_service.repository.MusicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

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

    public Mono<GetByUIdMusic> getByUIdMusic(String uuid) {
        return musicRepository.findByUuid(UUID.fromString(uuid))
                .map(music ->
                        new GetByUIdMusic(
                                music.getId(),
                                music.getUuid(),
                                music.getTitle(),
                                music.getAlbumId(),
                                music.getArtistId(),
                                music.getGenreId(),
                                music.getDuration(),
                                music.getLanguage()
                        ));
    }
}
