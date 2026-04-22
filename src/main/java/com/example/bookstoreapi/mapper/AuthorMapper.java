package com.example.bookstoreapi.mapper;

import com.example.bookstoreapi.dto.request.AuthorRequest;
import com.example.bookstoreapi.dto.response.AuthorResponse;
import com.example.bookstoreapi.entity.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    public Author toEntity(AuthorRequest request) {
        if (request == null) {
            return null;
        }

        return Author.builder()
                .name(request.getName())
                .biography(request.getBiography())
                .email(request.getEmail())
                .build();
    }

    public AuthorResponse toResponse(Author author) {
        if (author == null) {
            return null;
        }

        return AuthorResponse.builder()
                .id(author.getId())
                .name(author.getName())
                .biography(author.getBiography())
                .email(author.getEmail())
                .build();
    }

    public void updateEntityFromRequest(AuthorRequest request, Author author) {
        if (request == null || author == null) {
            return;
        }

        author.setName(request.getName());
        author.setBiography(request.getBiography());
        author.setEmail(request.getEmail());
    }
}