package com.lopply.music_service.controller;

import com.lopply.music_service.dto.request.music.CreateMusicRequest;
import com.lopply.music_service.dto.response.music.GetByUIdMusic;
import com.lopply.music_service.entity.Music;
import com.lopply.music_service.service.MusicServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

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

    @PostMapping("/create")
    public Mono<ApiResponse<Void>> createMusic(@RequestBody CreateMusicRequest request, ServerWebExchange exchange) {
        return musicService.createMusic(request)
                .map(ApiResponse::success)
                .onErrorResume(e ->
                        {
                            logger.error("api/music/create error {}", e.getMessage());
                            return Mono.just(ApiResponse.error());
                        }
                );
    }
}
