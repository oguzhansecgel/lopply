package com.lopply.auth_service.controller;

import com.lopply.auth_service.dto.GetSubscriberIdByUid;
import com.lopply.auth_service.repository.UserRepository;
import com.lopply.auth_service.service.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/get-user-id-by-uid")
    public ResponseEntity<GetSubscriberIdByUid> getUserByUid(@RequestParam("uid") UUID uid) {
        return userService.getSubscriberIdByUid(uid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
