package com.biblioteca.backend.book.service;

import com.biblioteca.backend.author.model.Author;
import com.biblioteca.backend.author.repository.AuthorRepository;
import com.biblioteca.backend.book.model.Book;
import com.biblioteca.backend.book.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public Book addBook(Book newBook) {
        Long authorID = newBook.getAuthor().getId();
        Optional<Author> optionalAuthor = authorRepository.findById(authorID);

        if(optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            newBook.setAuthor(author);
            return bookRepository.save(newBook);
        }
        return newBook;
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById((id));
    }

    public Optional<Book> findBook(Long id) {
        return bookRepository.findById(id);
    }

    public Book updateBook(Long id, Book updatedBook){
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
