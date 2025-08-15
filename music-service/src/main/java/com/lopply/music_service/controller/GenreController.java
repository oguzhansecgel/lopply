package com.lopply.music_service.controller;

import com.lopply.music_service.dto.request.genre.CreateGenreRequest;
import com.lopply.music_service.dto.response.genre.GetAllGenreResponse;
import com.lopply.music_service.entity.Genre;
import com.lopply.music_service.service.GenreServiceImpl;
import org.apache.kafka.shaded.com.google.protobuf.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/genre")
public class GenreController {
    private final GenreServiceImpl genreService;
    private Logger logger = LoggerFactory.getLogger(GenreController.class);

    public GenreController(GenreServiceImpl genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/all")
    public Mono<ApiResponse<List<GetAllGenreResponse>>> getAllGenres() {
        return genreService.getAllGenreResponse()
                .collectList()
                .map(ApiResponse::success)
                .onErrorResume(e -> {
                    logger.error("getAllGenres failed", e);
                    return Mono.just(ApiResponse.error());
                });
    }

    @GetMapping("/get-by-uid")
    public Mono<ApiResponse<Genre>> getGenreByUid(@RequestParam("uid") String uid) {
        logger.info("/api/genre/get-by-uid incoming request {}",uid);
        return genreService.getGenreByUid(uid)
                .map(genre -> ApiResponse.success(genre))
                .onErrorResume(e -> {
                    logger.error("getGenreByUid failed", e);
                    return Mono.just(ApiResponse.error());
                });
    }

    @PostMapping("/create")
    public Mono<ApiResponse<Void>> createGenre(@RequestBody CreateGenreRequest request) {
        logger.info("Create genre request: {}", request);

        return genreService.createGenre(request)
                .thenReturn(ApiResponse.<Void>success(null))
                .onErrorResume(e-> {
                    logger.error("createGenre failed", e);
                    return Mono.just(ApiResponse.<Void>error());
                });
    }
}
