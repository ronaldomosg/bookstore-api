package com.example.bookstoreapi.service;

import com.example.bookstoreapi.dto.request.OrderRequest;
import com.example.bookstoreapi.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse create(OrderRequest request, String userEmail);

    List<OrderResponse> getMyOrders(String userEmail);

    List<OrderResponse> getAll();

    OrderResponse confirm(Long orderId, String userEmail);

    OrderResponse cancel(Long orderId, String userEmail);
}
