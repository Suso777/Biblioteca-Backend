package com.biblioteca.backend.book.repository;

import com.biblioteca.backend.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
