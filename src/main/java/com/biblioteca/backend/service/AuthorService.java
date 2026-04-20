package com.biblioteca.backend.service;

import com.biblioteca.backend.model.Author;
import com.biblioteca.backend.repository.AuthorRepository;
import com.biblioteca.backend.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    public Optional<Author> findAuthor(Long id) {
        return authorRepository.findById(id);
    }

    public Author addAuthor(Author newAuthor) {
        return authorRepository.save(newAuthor);
    }

    public Author updateAuthor(Long id, Author updatedAuthor) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));

        existingAuthor.setName(updatedAuthor.getName());
        existingAuthor.setSurname(updatedAuthor.getSurname());
        existingAuthor.setNationality(updatedAuthor.getNationality());
        existingAuthor.setBirthYear(updatedAuthor.getBirthYear());
        existingAuthor.setAlive(updatedAuthor.getAlive());
        existingAuthor.setImage(updatedAuthor.getImage());

        return authorRepository.save(existingAuthor);
    }

    public void deleteAuthor(Long id) {
        if (bookRepository.existsByAuthorId(id)) {
            throw new IllegalStateException("Cannot delete author with books");
        }
        authorRepository.deleteById(id);
    }
}
