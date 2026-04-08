package com.biblioteca.backend.book.controller;

import com.biblioteca.backend.book.model.Book;
import com.biblioteca.backend.book.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<Book> getAllBooks(){
        return bookService.getAll();
    }

    @PostMapping("/books")
    public Book createdBook(@RequestBody Book newBook) {
        return bookService.addBook(newBook);
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable int id){
        bookService.deleteBook(id);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> findBookById(@PathVariable int id){
        Optional<Book> foundBook = bookService.findBook(id);

        if(foundBook.isPresent()){
            return new ResponseEntity<>(foundBook.get(), HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/books/{id")
    public ResponseEntity<Book> updatedBookById(@PathVariable int id, @RequestBody Book updatedBook){
        try{
            Book book = bookService.updateBook(id, updatedBook);
            return new ResponseEntity<>(book, HttpStatus.OK);
        }
        catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
