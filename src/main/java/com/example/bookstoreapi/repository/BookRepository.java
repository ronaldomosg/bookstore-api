package com.example.bookstoreapi.repository;

import com.example.bookstoreapi.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(attributePaths = {"author", "categories"})
    Page<Book> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"author", "categories"})
    Page<Book> findByAuthorId(Long authorId, Pageable pageable);

    @EntityGraph(attributePaths = {"author", "categories"})
    Page<Book> findByCategoriesId(Long categoryId, Pageable pageable);

    @EntityGraph(attributePaths = {"author", "categories"})
    Page<Book> findByAuthorIdAndCategoriesId(Long authorId, Long categoryId, Pageable pageable);

    boolean existsByAuthorId(Long authorId);
}



