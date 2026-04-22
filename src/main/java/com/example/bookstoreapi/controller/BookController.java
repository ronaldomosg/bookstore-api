package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.dto.request.BookRequest;
import com.example.bookstoreapi.dto.response.ApiResponse;
import com.example.bookstoreapi.dto.response.BookResponse;
import com.example.bookstoreapi.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<ApiResponse<BookResponse>> create(@Valid @RequestBody BookRequest request) {
        BookResponse book = bookService.create(request);
        return buildResponse(HttpStatus.CREATED, "Book created successfully", book);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> getById(@PathVariable Long id) {
        BookResponse book = bookService.getById(id);
        return buildResponse(HttpStatus.OK, "Book found successfully", book);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<BookResponse>>> getAll(
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) Long categoryId,
            Pageable pageable
    ) {
        Page<BookResponse> books = bookService.getAll(authorId, categoryId, pageable);
        return buildResponse(HttpStatus.OK, "Books retrieved successfully", books);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody BookRequest request
    ) {
        BookResponse book = bookService.update(id, request);
        return buildResponse(HttpStatus.OK, "Book updated successfully", book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        bookService.delete(id);
        return buildResponse(HttpStatus.OK, "Book deleted successfully", null);
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