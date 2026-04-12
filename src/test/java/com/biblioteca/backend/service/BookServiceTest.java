package com.biblioteca.backend.service;

import com.biblioteca.backend.model.Author;
import com.biblioteca.backend.model.Book;
import com.biblioteca.backend.repository.AuthorRepository;
import com.biblioteca.backend.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author();
        author.setId(1L);
        author.setName("Miguel");

        book = new Book();
        book.setId(10L);
        book.setTitle("Don Quijote");
        book.setAuthor(author);
    }

    @Test
    void testAddBookOk() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book result = bookService.addBook(book);

        assertNotNull(result);
        assertEquals("Don Quijote", result.getTitle());
        assertEquals(1L, result.getAuthor().getId());
        verify(authorRepository).findById(1L);
        verify(bookRepository).save(book);
    }

    @Test
    void testAddBookNoAuthor() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            bookService.addBook(book);
        });

        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void testUpdateBookOk() {
        Book updatedData = new Book();
        updatedData.setTitle("Quijote II");
        updatedData.setAuthor(author);

        when(bookRepository.findById(10L)).thenReturn(Optional.of(book));
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book result = bookService.updateBook(10L, updatedData);

        assertEquals("Quijote II", result.getTitle());
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void testDeleteBook() {
        bookService.deleteBook(10L);
        verify(bookRepository, times(1)).deleteById(10L);
    }
}