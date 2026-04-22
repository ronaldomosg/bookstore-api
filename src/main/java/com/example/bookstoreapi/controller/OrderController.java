package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.dto.request.OrderRequest;
import com.example.bookstoreapi.dto.response.ApiResponse;
import com.example.bookstoreapi.dto.response.OrderResponse;
import com.example.bookstoreapi.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> create(
            @Valid @RequestBody OrderRequest request,
            Authentication authentication
    ) {
        OrderResponse order = orderService.create(request, authentication.getName());
        return buildResponse(HttpStatus.CREATED, "Order created successfully", order);
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getMyOrders(Authentication authentication) {
        List<OrderResponse> orders = orderService.getMyOrders(authentication.getName());
        return buildResponse(HttpStatus.OK, "User orders retrieved successfully", orders);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAll() {
        List<OrderResponse> orders = orderService.getAll();
        return buildResponse(HttpStatus.OK, "Orders retrieved successfully", orders);
    }

    @PatchMapping("/{id}/confirm")
    public ResponseEntity<ApiResponse<OrderResponse>> confirm(
            @PathVariable Long id,
            Authentication authentication
    ) {
        OrderResponse order = orderService.confirm(id, authentication.getName());
        return buildResponse(HttpStatus.OK, "Order confirmed successfully", order);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<OrderResponse>> cancel(
            @PathVariable Long id,
            Authentication authentication
    ) {
        OrderResponse order = orderService.cancel(id, authentication.getName());
        return buildResponse(HttpStatus.OK, "Order cancelled successfully", order);
    }

    private <T> ResponseEntity<ApiResponse<T>> buildResponse(HttpStatus status, String message, T data) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .status("success")
                .code(status.value())
                .message(message)
                .data(data)
                .timestamp(Instant.now())
                .build();

        return new ResponseEntity<>(response, status);
    }
}