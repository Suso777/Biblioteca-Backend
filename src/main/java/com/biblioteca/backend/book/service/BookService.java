package com.biblioteca.backend.book.service;

import com.biblioteca.backend.book.model.Book;
import com.biblioteca.backend.book.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }

    public Optional<Book> findBook(int id) {
        return bookRepository.findById(id);
    }
}
