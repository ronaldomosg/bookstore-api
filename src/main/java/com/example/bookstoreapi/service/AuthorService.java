package com.example.bookstoreapi.service;

import com.example.bookstoreapi.dto.request.AuthorRequest;
import com.example.bookstoreapi.dto.response.AuthorResponse;
import com.example.bookstoreapi.dto.response.BookResponse;

import java.util.List;

public interface AuthorService {

    AuthorResponse create(AuthorRequest request);

    AuthorResponse update(Long id, AuthorRequest request);

    AuthorResponse getById(Long id);

    List<AuthorResponse> getAll();

    void delete(Long id);

    List<BookResponse> getBooksByAuthorId(Long authorId);
}