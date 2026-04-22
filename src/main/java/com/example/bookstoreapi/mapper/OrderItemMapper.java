package com.example.bookstoreapi.mapper;

import com.example.bookstoreapi.dto.response.OrderItemResponse;
import com.example.bookstoreapi.entity.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

    public OrderItemResponse toResponse(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }

        return OrderItemResponse.builder()
                .id(orderItem.getId())
                .bookId(orderItem.getBook() != null ? orderItem.getBook().getId() : null)
                .bookTitle(orderItem.getBook() != null ? orderItem.getBook().getTitle() : null)
                .quantity(orderItem.getQuantity())
                .unitPrice(orderItem.getUnitPrice())
                .subtotal(orderItem.getSubtotal())
                .build();
    }
}