package com.example.bookstoreapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiErrorResponse {

    private String status;
    private int code;
    private String message;
    private List<String> errors;
    private Instant timestamp;
    private String path;
}
