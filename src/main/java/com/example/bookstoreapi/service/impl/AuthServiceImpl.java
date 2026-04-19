package com.example.bookstoreapi.service.impl;

import com.example.bookstoreapi.dto.request.LoginRequest;
import com.example.bookstoreapi.dto.request.RegisterRequest;
import com.example.bookstoreapi.dto.response.AuthResponse;
import com.example.bookstoreapi.entity.Role;
import com.example.bookstoreapi.entity.User;
import com.example.bookstoreapi.repository.UserRepository;
import com.example.bookstoreapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(null)
                .message("User registered successfully")
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return AuthResponse.builder()
                .token(null)
                .message("Login successful")
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}