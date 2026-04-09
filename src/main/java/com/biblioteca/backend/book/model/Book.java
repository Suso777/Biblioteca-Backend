package com.biblioteca.backend.book.model;


import com.biblioteca.backend.author.model.Author;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name="books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String ISBN;
    private int publicationYear;
    private String image;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonIgnoreProperties("products")
    private Author author;

    public Book(String title, String ISBN, int publicationYear, String image, Author author) {
        this.title = title;
        this.ISBN = ISBN;
        this.publicationYear = publicationYear;
        this.image = image;
        this.author = author;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
