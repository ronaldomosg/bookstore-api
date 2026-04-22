package com.example.bookstoreapi.exception.custom;

public class AuthorHasBooksException extends RuntimeException {

    public AuthorHasBooksException(String message) {
        super(message);
    }
}