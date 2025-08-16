package com.lopply.music_service.service;

import com.lopply.music.MusicServiceGrpc;
import com.lopply.music.Musicservice;
import com.lopply.music_service.dto.request.music.CreateMusicRequest;
import com.lopply.music_service.dto.response.music.GetByUIdMusic;
import com.lopply.music_service.entity.Music;
import com.lopply.music_service.repository.MusicRepository;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
public class MusicServiceImpl extends MusicServiceGrpc.MusicServiceImplBase {

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

    @Override
    public void getMusicByUId(Musicservice.MusicUIdRequest request, StreamObserver<Musicservice.MusicById> responseObserver) {
        logger.info("Incoming get music request {}", request);

        musicRepository.findByUuid(UUID.fromString(request.getUid()))
                .switchIfEmpty(Mono.error(new StatusRuntimeException(Status.NOT_FOUND.withDescription("Music Not Found"))))
                .map(music -> Musicservice.MusicById.newBuilder().setId(music.getId()).build())
                .subscribe(responseObserver::onNext, responseObserver::onError, responseObserver::onCompleted);
    }
}
