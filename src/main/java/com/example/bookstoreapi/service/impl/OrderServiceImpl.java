package com.example.bookstoreapi.service.impl;

import com.example.bookstoreapi.dto.request.OrderItemRequest;
import com.example.bookstoreapi.dto.request.OrderRequest;
import com.example.bookstoreapi.dto.response.OrderResponse;
import com.example.bookstoreapi.entity.Book;
import com.example.bookstoreapi.entity.Order;
import com.example.bookstoreapi.entity.OrderItem;
import com.example.bookstoreapi.entity.OrderStatus;
import com.example.bookstoreapi.entity.User;
import com.example.bookstoreapi.exception.custom.InsufficientStockException;
import com.example.bookstoreapi.exception.custom.InvalidOrderStateException;
import com.example.bookstoreapi.exception.custom.ResourceNotFoundException;
import com.example.bookstoreapi.exception.custom.UnauthorizedAccessException;
import com.example.bookstoreapi.mapper.OrderMapper;
import com.example.bookstoreapi.repository.BookRepository;
import com.example.bookstoreapi.repository.OrderRepository;
import com.example.bookstoreapi.repository.UserRepository;
import com.example.bookstoreapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponse create(OrderRequest request, String userEmail) {
        User user = findUserByEmail(userEmail);

        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.PENDING)
                .total(BigDecimal.ZERO)
                .createdAt(Instant.now())
                .items(new ArrayList<>())
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : request.getItems()) {
            Book book = bookRepository.findById(itemRequest.getBookId())
                    .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + itemRequest.getBookId()));

            validateStock(book, itemRequest.getQuantity());

            BigDecimal unitPrice = book.getPrice();
            BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .book(book)
                    .quantity(itemRequest.getQuantity())
                    .unitPrice(unitPrice)
                    .subtotal(subtotal)
                    .build();

            order.getItems().add(orderItem);
            total = total.add(subtotal);
        }

        order.setTotal(total);

        Order savedOrder = orderRepository.save(order);
        return orderMapper.toResponse(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getMyOrders(String userEmail) {
        User user = findUserByEmail(userEmail);

        return orderRepository.findByUserId(user.getId())
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public OrderResponse confirm(Long orderId, String userEmail) {
        Order order = findOrderById(orderId);
        validateOwner(order, userEmail);

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new InvalidOrderStateException("Only pending orders can be confirmed");
        }

        for (OrderItem item : order.getItems()) {
            Book book = item.getBook();
            validateStock(book, item.getQuantity());
            book.setStock(book.getStock() - item.getQuantity());
            bookRepository.save(book);
        }

        order.setStatus(OrderStatus.CONFIRMED);

        Order savedOrder = orderRepository.save(order);
        return orderMapper.toResponse(savedOrder);
    }

    @Override
    @Transactional
    public OrderResponse cancel(Long orderId, String userEmail) {
        Order order = findOrderById(orderId);
        validateOwner(order, userEmail);

        if (order.getStatus() == OrderStatus.CONFIRMED) {
            throw new InvalidOrderStateException("Confirmed orders cannot be cancelled");
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new InvalidOrderStateException("Order is already cancelled");
        }

        order.setStatus(OrderStatus.CANCELLED);

        Order savedOrder = orderRepository.save(order);
        return orderMapper.toResponse(savedOrder);
    }

    private User findUserByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));
    }

    private Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
    }

    private void validateStock(Book book, Integer quantity) {
        if (book.getStock() < quantity) {
            throw new InsufficientStockException(
                    "Insufficient stock for book with id: " + book.getId()
            );
        }
    }

    private void validateOwner(Order order, String userEmail) {
        if (order.getUser() == null || !order.getUser().getEmail().equals(userEmail)) {
            throw new UnauthorizedAccessException("User cannot access this order");
        }
    }
}