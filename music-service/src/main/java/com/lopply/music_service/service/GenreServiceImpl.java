package com.lopply.music_service.service;

import com.lopply.music_service.dto.request.genre.CreateGenreRequest;
import com.lopply.music_service.dto.response.genre.GetAllGenreResponse;
import com.lopply.music_service.entity.Genre;
import com.lopply.music_service.repository.GenreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GenreServiceImpl {

    private final GenreRepository genreRepository;
    private Logger logger = LoggerFactory.getLogger(GenreServiceImpl.class);

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Flux<GetAllGenreResponse> getAllGenreResponse(){
        logger.info("getAllGenreResponse");
        return genreRepository.findAll()
                .map(genre ->
                    new GetAllGenreResponse(
                            genre.getId(),
                            genre.getUuid(),
                            genre.getName()
                    ));
    }

    public Mono<Genre> getGenreByUid(String uid){
        return genreRepository.findByUuid(uid)
                .switchIfEmpty(Mono.defer(()->{
                    logger.warn("Genre Not Found For UId {}", uid);
                    return Mono.error(new RuntimeException("Genre Not Found For UId " + uid));
                }));
    }

    public Mono<Void> createGenre(CreateGenreRequest request) {
        Genre genre = new Genre();
        genre.setName(request.getName());

        return genreRepository.save(genre)
                .doOnNext(savedGenre -> {logger.info("genre saved {}", savedGenre);})
                .then();
    }
}
