package com.example.bookstoreapi.mapper;

import com.example.bookstoreapi.dto.response.OrderItemResponse;
import com.example.bookstoreapi.dto.response.OrderResponse;
import com.example.bookstoreapi.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final OrderItemMapper orderItemMapper;

    public OrderResponse toResponse(Order order) {
        if (order == null) {
            return null;
        }

        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUser() != null ? order.getUser().getId() : null)
                .userEmail(order.getUser() != null ? order.getUser().getEmail() : null)
                .status(order.getStatus())
                .total(order.getTotal())
                .createdAt(order.getCreatedAt())
                .items(mapItems(order))
                .build();
    }

    private List<OrderItemResponse> mapItems(Order order) {
        if (order.getItems() == null) {
            return Collections.emptyList();
        }

        return order.getItems()
                .stream()
                .map(orderItemMapper::toResponse)
                .toList();
    }
}