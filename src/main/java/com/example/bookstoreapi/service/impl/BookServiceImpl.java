package com.example.bookstoreapi.service.impl;

import com.example.bookstoreapi.dto.request.BookRequest;
import com.example.bookstoreapi.dto.response.BookResponse;
import com.example.bookstoreapi.entity.Author;
import com.example.bookstoreapi.entity.Book;
import com.example.bookstoreapi.entity.Category;
import com.example.bookstoreapi.exception.custom.ResourceNotFoundException;
import com.example.bookstoreapi.mapper.BookMapper;
import com.example.bookstoreapi.repository.AuthorRepository;
import com.example.bookstoreapi.repository.BookRepository;
import com.example.bookstoreapi.repository.CategoryRepository;
import com.example.bookstoreapi.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;

    @Override
    public BookResponse create(BookRequest request) {
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + request.getAuthorId()));

        List<Category> categories = categoryRepository.findAllById(request.getCategoryIds());

        if (categories.size() != request.getCategoryIds().size()) {
            throw new ResourceNotFoundException("One or more categories were not found");
        }

        Book book = bookMapper.toEntity(request);
        book.setAuthor(author);
        book.setCategories(categories);

        Book savedBook = bookRepository.save(book);
        return bookMapper.toResponse(savedBook);
    }

    @Override
    public BookResponse getById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        return bookMapper.toResponse(book);
    }

    @Override
    public BookResponse update(Long id, BookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + request.getAuthorId()));

        List<Category> categories = categoryRepository.findAllById(request.getCategoryIds());

        if (categories.size() != request.getCategoryIds().size()) {
            throw new ResourceNotFoundException("One or more categories were not found");
        }

        bookMapper.updateEntityFromRequest(request, book);
        book.setAuthor(author);
        book.setCategories(categories);

        Book updatedBook = bookRepository.save(book);
        return bookMapper.toResponse(updatedBook);
    }

    @Override
    public void delete(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        bookRepository.delete(book);
    }

    @Override
    public Page<BookResponse> getAll(Long authorId, Long categoryId, Pageable pageable) {
        Page<Book> books;

        if (authorId != null && categoryId != null) {
            books = bookRepository.findByAuthorIdAndCategoriesId(authorId, categoryId, pageable);
        } else if (authorId != null) {
            books = bookRepository.findByAuthorId(authorId, pageable);
        } else if (categoryId != null) {
            books = bookRepository.findByCategoriesId(categoryId, pageable);
        } else {
            books = bookRepository.findAll(pageable);
        }

        return books.map(bookMapper::toResponse);
    }
}