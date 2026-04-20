package com.example.bookstoreapi.service.impl;

import com.example.bookstoreapi.dto.request.LoginRequest;
import com.example.bookstoreapi.dto.request.RegisterRequest;
import com.example.bookstoreapi.dto.response.AuthResponse;
import com.example.bookstoreapi.entity.Role;
import com.example.bookstoreapi.entity.User;
import com.example.bookstoreapi.exception.custom.EmailAlreadyExistsException;
import com.example.bookstoreapi.exception.custom.InvalidCredentialsException;
import com.example.bookstoreapi.repository.UserRepository;
import com.example.bookstoreapi.security.JwtService;
import com.example.bookstoreapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        return AuthResponse.builder()
                .token(token)
                .message("User registered successfully")
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EmailAlreadyExistsException("Email already exists"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return AuthResponse.builder()
                .token(token)
                .message("Login successful")
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}