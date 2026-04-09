package com.biblioteca.backend.service;

import com.biblioteca.backend.model.Author;
import com.biblioteca.backend.repository.AuthorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
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

        return authorRepository.save(existingAuthor);
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
