package com.biblioteca.backend.book.service;

import com.biblioteca.backend.book.model.Book;
import com.biblioteca.backend.book.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) { this.bookRepository = bookRepository; }

    public Book addBook(Book newBook) {
        return bookRepository.save(newBook);
    }
    public List<Book> getAll() {
        return bookRepository.findAll();
    }
}
