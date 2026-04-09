package com.biblioteca.backend.controller;

import com.biblioteca.backend.model.Book;
import com.biblioteca.backend.service.BookService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
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
    public void deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> findBookById(@PathVariable Long id){
        Optional<Book> foundBook = bookService.findBook(id);

        if(foundBook.isPresent()){
            return new ResponseEntity<>(foundBook.get(), HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/books/{id}")
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
