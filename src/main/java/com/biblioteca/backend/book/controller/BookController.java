package com.biblioteca.backend.book.controller;

import com.biblioteca.backend.book.model.Book;
import com.biblioteca.backend.book.service.BookService;
import org.springframework.web.bind.annotation.*;

public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public Book createdBook(@RequestBody Book newBook) {
        return bookService.addBook(newBook);
    }
}
