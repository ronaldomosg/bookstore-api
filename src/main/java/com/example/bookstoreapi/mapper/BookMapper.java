package com.example.bookstoreapi.mapper;

import com.example.bookstoreapi.dto.request.BookRequest;
import com.example.bookstoreapi.dto.response.BookResponse;
import com.example.bookstoreapi.entity.Book;
import com.example.bookstoreapi.entity.Category;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class BookMapper {

    public Book toEntity(BookRequest request) {
        if (request == null) {
            return null;
        }

        return Book.builder()
                .title(request.getTitle())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .stock(request.getStock())
                .build();
    }

    public BookResponse toResponse(Book book) {
        if (book == null) {
            return null;
        }

        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .price(book.getPrice())
                .stock(book.getStock())
                .authorId(book.getAuthor() != null ? book.getAuthor().getId() : null)
                .authorName(book.getAuthor() != null ? book.getAuthor().getName() : null)
                .categoryIds(extractCategoryIds(book.getCategories()))
                .categoryNames(extractCategoryNames(book.getCategories()))
                .build();
    }

    public void updateEntityFromRequest(BookRequest request, Book book) {
        if (request == null || book == null) {
            return;
        }

        book.setTitle(request.getTitle());
        book.setIsbn(request.getIsbn());
        book.setPrice(request.getPrice());
        book.setStock(request.getStock());

        // author y categories NO se asignan aquÃ­
        // eso se hace en el service buscando por ID
    }

    private List<Long> extractCategoryIds(List<Category> categories) {
        if (categories == null) {
            return Collections.emptyList();
        }

        return categories.stream()
                .map(Category::getId)
                .toList();
    }

    private List<String> extractCategoryNames(List<Category> categories) {
        if (categories == null) {
            return Collections.emptyList();
        }

        return categories.stream()
                .map(Category::getName)
                .toList();
    }
}