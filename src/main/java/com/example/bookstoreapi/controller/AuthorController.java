package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.dto.request.AuthorRequest;
import com.example.bookstoreapi.dto.response.ApiResponse;
import com.example.bookstoreapi.dto.response.AuthorResponse;
import com.example.bookstoreapi.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.bookstoreapi.dto.response.BookResponse;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<ApiResponse<AuthorResponse>> create(@Valid @RequestBody AuthorRequest request) {
        AuthorResponse author = authorService.create(request);
        return buildResponse(HttpStatus.CREATED, "Author created successfully", author);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AuthorResponse>> getById(@PathVariable Long id) {
        AuthorResponse author = authorService.getById(id);
        return buildResponse(HttpStatus.OK, "Author found successfully", author);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AuthorResponse>>> getAll() {
        List<AuthorResponse> authors = authorService.getAll();
        return buildResponse(HttpStatus.OK, "Authors retrieved successfully", authors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AuthorResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody AuthorRequest request
    ) {
        AuthorResponse author = authorService.update(id, request);
        return buildResponse(HttpStatus.OK, "Author updated successfully", author);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        authorService.delete(id);
        return buildResponse(HttpStatus.OK, "Author deleted successfully", null);
    }
    @GetMapping("/{id}/books")
    public ResponseEntity<ApiResponse<List<BookResponse>>> getBooksByAuthorId(@PathVariable Long id) {
        List<BookResponse> books = authorService.getBooksByAuthorId(id);
        return buildResponse(HttpStatus.OK, "Author books retrieved successfully", books);
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