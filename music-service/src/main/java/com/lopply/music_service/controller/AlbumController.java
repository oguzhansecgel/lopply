package com.lopply.music_service.controller;

import com.lopply.music_service.dto.request.album.CreateAlbumRequest;
import com.lopply.music_service.entity.Album;
import com.lopply.music_service.service.AlbumServiceImpl;
import org.apache.kafka.shaded.com.google.protobuf.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/album")
public class AlbumController {

    private final AlbumServiceImpl albumService;
    private Logger logger = LoggerFactory.getLogger(AlbumController.class);

    public AlbumController(AlbumServiceImpl albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/get-by-uid")
    public Mono<ApiResponse<Album>> getByUid(@RequestParam("uid") String uid) {
        logger.info("/api/album/get-by-uid incoming request {}",uid);

        return albumService.getAlbumByUId(uid)
                .map(album -> ApiResponse.success(album))
                .onErrorResume(e -> {
                    logger.error("/api/album/get-by-uid failed", e);
                    return Mono.just(ApiResponse.error());
                });
    }

    @PostMapping("/create")
    public Mono<ApiResponse<Void>> createAlbum(@RequestBody CreateAlbumRequest request) {
        logger.info("/api/album/create incoming request {}", request);

        return albumService.createAlbum(request)
                .then(Mono.just(ApiResponse.<Void>success( null)))
                .onErrorResume(e -> {
                    logger.error("/api/album/create failed", e);
                    return Mono.just(ApiResponse.error());
                });
    }
}
