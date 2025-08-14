package com.lopply.music_service.controller;

import com.lopply.music_service.dto.request.music.CreateMusicRequest;
import com.lopply.music_service.entity.Music;
import com.lopply.music_service.service.MusicServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/create")
    public Mono<ResponseEntity<Void>> createMusic(@RequestBody CreateMusicRequest request, ServerWebExchange exchange) {
        return musicService.createMusic(request)
                .map(ResponseEntity::ok)
                .onErrorResume(e ->
                        {
                            logger.error("api/music/create error {}", e.getMessage());
                            return Mono.just(ResponseEntity.badRequest().build());
                        }
                );
    }
}
