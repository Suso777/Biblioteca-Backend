package com.biblioteca.backend.repository;

import com.biblioteca.backend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByAuthorId(Long authorId);
}
