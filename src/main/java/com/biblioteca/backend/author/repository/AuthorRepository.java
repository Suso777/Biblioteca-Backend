package com.biblioteca.backend.author.repository;

import com.biblioteca.backend.author.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
