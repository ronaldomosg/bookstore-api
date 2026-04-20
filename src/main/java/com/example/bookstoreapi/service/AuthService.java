package com.example.bookstoreapi.service;

import com.example.bookstoreapi.dto.request.LoginRequest;
import com.example.bookstoreapi.dto.request.RegisterRequest;
import com.example.bookstoreapi.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}