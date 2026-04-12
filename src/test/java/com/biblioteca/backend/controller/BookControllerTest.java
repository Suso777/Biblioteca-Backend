package com.biblioteca.backend.controller;

import com.biblioteca.backend.model.Book;
import com.biblioteca.backend.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    private Book exampleBook;

    @BeforeEach
    void setUp(){
        exampleBook = new Book();
        exampleBook.setId(1L);
        exampleBook.setTitle("Don Quijote de la Mancha");
        exampleBook.setIsbn("8496067130");
        exampleBook.setPublicationYear(1605);
        exampleBook.setImage("https://covers.openlibrary.org/b/isbn/8496067130-L.jpg");
    }

    @Test
    void getAllBooks() throws Exception{
        when(bookService.getAll()).thenReturn(List.of(exampleBook));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Don Quijote de la Mancha"))
                .andExpect(jsonPath("$[0].isbn").value("8496067130"));
    }

    @Test
    void createdBook() throws Exception {
        when(bookService.addBook(any(Book.class))).thenReturn(exampleBook);

        String bookJson = "{\"title\":\"Don Quijote de la Mancha\", \"isbn\":\"8496067130\", \"publicationYear\":1605, \"image\":\"https://covers.openlibrary.org/b/isbn/8496067130-L.jpg\"}";

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
                .andExpect(status().isCreated());
    }

    @Test
    void deleteBook() throws Exception{
        when(bookService.findBook(1L)).thenReturn(Optional.of(exampleBook));

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void findBookById() throws Exception{
        when(bookService.findBook(1L)).thenReturn(Optional.ofNullable(exampleBook));

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Don Quijote de la Mancha"))
                .andExpect(jsonPath("$.isbn").value("8496067130"));
    }

    @Test
    void updatedBookById()  throws Exception{
        exampleBook.setTitle("Don Quijote Actualizado");

        when(bookService.findBook(1L)).thenReturn(Optional.of(exampleBook));

        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(exampleBook);

        String bookJson = "{\"title\":\"Don Quijote Actualizado\", \"isbn\":\"8496067130\", \"publicationYear\":1605, \"image\":\"https://covers.openlibrary.org/b/isbn/8496067130-L.jpg\"}";

        mockMvc.perform(put("/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Don Quijote Actualizado"));
    }
}