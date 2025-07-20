package com.taskmanager.service.auth;

import com.taskmanager.dto.auth.AuthResponseDto;
import com.taskmanager.dto.auth.LoginDto;
import com.taskmanager.dto.auth.RegisterDto;
import com.taskmanager.entity.User;
import com.taskmanager.enums.Role;
import com.taskmanager.enums.UserStatus;
import com.taskmanager.repository.UserRepository;
import com.taskmanager.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDto register(RegisterDto request) {
        // Check if username already exists
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username '" + request.getUsername() + "' is already taken. Please choose a different username.");
        }
        
        var user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .role(Role.ROLE_USER)
                .status(UserStatus.INACTIVE)
                .build();
        
        try {
            userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthResponseDto.builder()
                    .token(jwtToken)
                    .build();
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Username '" + request.getUsername() + "' is already taken. Please choose a different username.");
        }
    }

    public AuthResponseDto login(LoginDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthResponseDto.builder()
                .token(jwtToken)
                .build();
    }
} 