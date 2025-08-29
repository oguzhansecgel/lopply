package com.lopply.music_service.controller;

import com.lopply.music_service.dto.request.music.CreateMusicRequest;
import com.lopply.music_service.dto.response.music.GetAllArtistMusic;
import com.lopply.music_service.dto.response.music.GetByUIdMusic;
import com.lopply.music_service.dto.response.music.GetMusicWithArtist;
import com.lopply.music_service.service.MusicServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/music")
public class MusicController {

    private final MusicServiceImpl musicService;
    private Logger logger = LoggerFactory.getLogger(MusicController.class);
    public MusicController(MusicServiceImpl musicService) {
        this.musicService = musicService;
    }

    @GetMapping("/get-by-uid")
    public Mono<ApiResponse<GetByUIdMusic>> getByUid(@RequestParam String uid) {
        logger.info("Incoming get music by uid {}", uid);
        return musicService.getByUIdMusic(uid)
                .map( music ->
                        ApiResponse.success(music))
                .switchIfEmpty(Mono.just(ApiResponse.error()))
                .onErrorResume(e ->
                        {
                            logger.error("api/music/get-by-uid error {}", e.getMessage());
                            return Mono.just(ApiResponse.error());
                        }
                );
    }

    @GetMapping("/music-with-artist")
    public Mono<ApiResponse<GetMusicWithArtist>> getMusicWithArtist(@RequestParam("musicUId") String musicUId) {
        logger.info("[/music/music-with-artist] Incoming request musicUId: {}", musicUId);
        return musicService.getMusicWithArtist(musicUId)
                .map(ApiResponse::success)
                .onErrorResume( e -> {
                    logger.error("api/music/get-music-with-artist error {}", e.getMessage());
                    return Mono.just(ApiResponse.error());
                });
    }

    @GetMapping("/get-all-artist-music")
    public Mono<ApiResponse<List<GetAllArtistMusic>>> getAllArtistMusic(@RequestParam String artistUId) {
        logger.info("[/music/get-all-artist-music] Incoming request artistUId: {}", artistUId);
        return musicService.getAllArtistMusic(artistUId)
                .collectList()
                .map(ApiResponse::success)
                .onErrorResume(e ->{
                    logger.error("api/music/get-all-artist-music error {}", e.getMessage());
                    return Mono.just(ApiResponse.error());
                });
    }

    @PostMapping("/create")
    public Mono<ApiResponse<Void>> createMusic(@RequestBody CreateMusicRequest request, ServerWebExchange exchange) {
        logger.info("[/music/create] Incoming request: {}", request);
        return musicService.createMusicWithZip(request)
                .map(ApiResponse::success)
                .onErrorResume(e ->
                        {
                            logger.error("api/music/create error {}", e.getMessage());
                            return Mono.just(ApiResponse.error());
                        }
                );
    }
}
