package com.example.bookstoreapi.service;

import com.example.bookstoreapi.dto.request.BookRequest;
import com.example.bookstoreapi.dto.response.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    BookResponse create(BookRequest request);

    BookResponse update(Long id, BookRequest request);

    BookResponse getById(Long id);

    Page<BookResponse> getAll(Long authorId, Long categoryId, Pageable pageable);

    void delete(Long id);
}