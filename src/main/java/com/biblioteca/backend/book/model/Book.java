package com.biblioteca.backend.book.model;


import com.biblioteca.backend.author.model.Author;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;
    @NotBlank
    private String ISBN;
    @NotNull
    private int publicationYear;
    @NotBlank
    private String image;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonIgnoreProperties("products")
    private Author author;
}
