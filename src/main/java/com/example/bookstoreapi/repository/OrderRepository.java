package com.example.bookstoreapi.repository;

import com.example.bookstoreapi.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"user", "items", "items.book"})
    List<Order> findByUserId(Long userId);

    @Override
    @EntityGraph(attributePaths = {"user", "items", "items.book"})
    List<Order> findAll();
}