package com.biblioteca.backend.service;

import com.biblioteca.backend.model.Author;
import com.biblioteca.backend.model.Book;
import com.biblioteca.backend.repository.AuthorRepository;
import com.biblioteca.backend.repository.BookRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public Book addBook(Book newBook) {
        Author author = getAuthorFromPayload(newBook);
        newBook.setAuthor(author);
        return bookRepository.save(newBook);
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
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found with id: " + id));

        Author author = getAuthorFromPayload(updatedBook);

        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setIsbn(updatedBook.getIsbn());
        existingBook.setPublicationYear(updatedBook.getPublicationYear());
        existingBook.setImage(updatedBook.getImage());
        existingBook.setAuthor(author);

        return bookRepository.save(existingBook);
    }

    private Author getAuthorFromPayload(Book book) {
        if (book.getAuthor() == null || book.getAuthor().getId() == null) {
            throw new IllegalArgumentException("Author id is required");
        }

        Long authorId = book.getAuthor().getId();
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new NoSuchElementException("Author not found with id: " + authorId));
    }
}
