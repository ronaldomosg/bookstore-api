package com.example.bookstoreapi.service;

import com.example.bookstoreapi.dto.request.CategoryRequest;
import com.example.bookstoreapi.dto.response.BookResponse;
import com.example.bookstoreapi.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse create(CategoryRequest request);

    CategoryResponse update(Long id, CategoryRequest request);

    CategoryResponse getById(Long id);

    List<CategoryResponse> getAll();

    void delete(Long id);

    List<BookResponse> getBooksByCategoryId(Long categoryId);
}