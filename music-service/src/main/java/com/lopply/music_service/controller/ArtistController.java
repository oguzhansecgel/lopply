package com.lopply.music_service.controller;

import com.lopply.music_service.dto.request.artist.CreateArtistRequest;
import com.lopply.music_service.entity.Artist;
import com.lopply.music_service.service.ArtistServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/artist")
public class ArtistController {

    private final ArtistServiceImpl artistService;
    private Logger logger = LoggerFactory.getLogger(ArtistController.class);

    public ArtistController(ArtistServiceImpl artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/get-by-uid")
    public Mono<ApiResponse<Artist>> getByUid(@RequestParam("uid") String uid) {
        logger.info("/api/artist/get-by-uid incoming request {}",uid);
        return artistService.getArtistByUId(uid)
                .map(artist -> ApiResponse.success(artist))
                .onErrorResume(e -> {
                    logger.error(e.getMessage());
                    return Mono.just(ApiResponse.error());
                });
    }

    @PostMapping("/create")
    public Mono<ApiResponse<Void>> createArtist(@RequestBody CreateArtistRequest request) {
        logger.info("/api/artist/create-artist incoming request {}",request);
        return artistService.createArtist(request)
                .then(Mono.just(ApiResponse.<Void>success(null)))
                .onErrorResume(e ->{
                    logger.warn("/api/artist/create-artist failed {}",e.getMessage());
                    return Mono.just(ApiResponse.error());
                });
    }
}
