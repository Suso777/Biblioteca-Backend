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

    public Book updateBook(int id, Book updatedBook){
        Optional<Book> foundBook = bookRepository.findById(id);

        if(foundBook.isPresent()){
            Book existingBook = foundBook.get();

            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setISBN(updatedBook.getISBN());
            existingBook.setPublicationYear(updatedBook.getPublicationYear());
            existingBook.setImage(updatedBook.getImage());

            return bookRepository.save(existingBook);
        }

        throw new RuntimeException("We can't find any book with that ID");
    }
}
