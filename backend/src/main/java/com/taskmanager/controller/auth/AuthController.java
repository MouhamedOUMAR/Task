package com.taskmanager.controller.auth;

import com.taskmanager.dto.auth.AuthResponseDto;
import com.taskmanager.dto.auth.LoginDto;
import com.taskmanager.dto.auth.RegisterDto;
import com.taskmanager.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterDto request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto request) {
        return ResponseEntity.ok(authService.login(request));
    }
} 