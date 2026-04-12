package com.biblioteca.backend.service;

import com.biblioteca.backend.model.Author;
import com.biblioteca.backend.repository.AuthorRepository;
import com.biblioteca.backend.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks // Inyecta automáticamente los mocks en el servicio
    private AuthorService authorService;

    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author();
        author.setId(1L);
        author.setName("Miguel");
        author.setSurname("de Cervantes");
    }

    @Test
    void testFindAuthorOk() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        Optional<Author> result = authorService.findAuthor(1L);

        assertTrue(result.isPresent());
        assertEquals("Miguel", result.get().getName());
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateAuthorOk() {
        Author updatedData = new Author();
        updatedData.setName("Miguel Actualizado");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        Author result = authorService.updateAuthor(1L, updatedData);

        assertEquals("Miguel Actualizado", result.getName());
        verify(authorRepository).save(author);
    }

    @Test
    void testDeleteAuthorNotOkIfHaveBooks() {
        when(bookRepository.existsByAuthorId(1L)).thenReturn(true);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            authorService.deleteAuthor(1L);
        });

        assertEquals("Cannot delete author with books", exception.getMessage());

        verify(authorRepository, never()).deleteById(anyLong());
    }

    @Test
    void testDeleteAuthorOkIfNoBooks() {
        when(bookRepository.existsByAuthorId(1L)).thenReturn(false);

        authorService.deleteAuthor(1L);

        verify(authorRepository, times(1)).deleteById(1L);
    }
}