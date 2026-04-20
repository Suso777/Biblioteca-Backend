package com.biblioteca.backend.controller;

import com.biblioteca.backend.model.Book;
import com.biblioteca.backend.service.BookService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@CrossOrigin(origins = "http://localhost:3000")
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
    public ResponseEntity<Book> createdBook(@Valid @RequestBody Book newBook) {
        try {
            Book savedBook = bookService.addBook(newBook);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().build();
        } catch (NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        if (bookService.findBook(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findBookById(@PathVariable Long id){
        Optional<Book> foundBook = bookService.findBook(id);

        if(foundBook.isPresent()){
            return ResponseEntity.ok(foundBook.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updatedBookById(@PathVariable Long id, @Valid @RequestBody Book updatedBook){
        try{
            Book book = bookService.updateBook(id, updatedBook);
            return ResponseEntity.ok(book);
        }
        catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().build();
        }
        catch (NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
