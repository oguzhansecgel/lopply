package com.lopply.auth_service.service;

import com.lopply.auth_service.config.JwtService;
import com.lopply.auth_service.dto.*;
import com.lopply.auth_service.entity.Role;
import com.lopply.auth_service.entity.User;
import com.lopply.auth_service.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager  authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public RegisterResponse register(RegisterRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        user.setUuid(UUID.randomUUID());
        User savedUser =  userRepository.save(user);
        Map<String, Object> claims = new HashMap<>();
        claims.put("UUID", savedUser.getUuid());
        claims.put("account_id", savedUser.getId());
        return new RegisterResponse(savedUser.getEmail(), savedUser.getPassword());

    }

    public LoginResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("Kullanıcı bulunamadı")
        );

        Map<String, Object> claims = new HashMap<>();
        claims.put("UUID", user.getUuid());
        claims.put("account_id", user.getId());
        String jwtToken = jwtService.generateToken(claims, user);

        return new LoginResponse(jwtToken, user.getId());
    }
}