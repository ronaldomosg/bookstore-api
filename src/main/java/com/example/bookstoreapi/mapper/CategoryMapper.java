package com.example.bookstoreapi.mapper;

import com.example.bookstoreapi.dto.request.CategoryRequest;
import com.example.bookstoreapi.dto.response.CategoryResponse;
import com.example.bookstoreapi.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequest request) {
        if (request == null) {
            return null;
        }

        return Category.builder()
                .name(request.getName())
                .build();
    }

    public CategoryResponse toResponse(Category category) {
        if (category == null) {
            return null;
        }

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public void updateEntityFromRequest(CategoryRequest request, Category category) {
        if (request == null || category == null) {
            return;
        }

        category.setName(request.getName());
    }
}