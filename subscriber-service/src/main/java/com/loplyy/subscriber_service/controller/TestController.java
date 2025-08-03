package com.loplyy.subscriber_service.controller;

import com.loplyy.subscriber_service.config.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/test")
public class TestController {


    private final JwtService jwtService;

    public TestController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("/uuid/test")
    public Mono<ResponseEntity<String>> test(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Token yok"));
        }

        String token = authHeader.substring(7);
        Claims claims;

        try {
            claims = jwtService.extractAllClaims(token);
        } catch (Exception e) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Token geçersiz"));
        }

        String uuid = claims.get("UUID", String.class);
        return Mono.just(ResponseEntity.ok("Token'dan gelen UUID: " + uuid));
    }

}
