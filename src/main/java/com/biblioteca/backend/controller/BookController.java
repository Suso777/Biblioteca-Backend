package com.biblioteca.backend.controller;

import com.biblioteca.backend.model.Book;
import com.biblioteca.backend.service.BookService;
import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks(){
        return bookService.getAll();
    }

    @PostMapping
    public Book createdBook(@Valid @RequestBody Book newBook) {
        return bookService.addBook(newBook);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findBookById(@PathVariable Long id){
        Optional<Book> foundBook = bookService.findBook(id);

        if(foundBook.isPresent()){
            return new ResponseEntity<>(foundBook.get(), HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updatedBookById(@PathVariable Long id, @RequestBody Book updatedBook){
        try{
            Book book = bookService.updateBook(id, updatedBook);
            return new ResponseEntity<>(book, HttpStatus.OK);
        }
        catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
