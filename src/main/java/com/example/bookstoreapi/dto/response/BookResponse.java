package com.example.bookstoreapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponse {

    private Long id;
    private String title;
    private String isbn;
    private BigDecimal price;
    private Integer stock;
    private Long authorId;
    private String authorName;
    private List<Long> categoryIds;
    private List<String> categoryNames;
}
