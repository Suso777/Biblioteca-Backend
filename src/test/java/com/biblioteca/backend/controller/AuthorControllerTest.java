package com.biblioteca.backend.controller;

import com.biblioteca.backend.model.Author;
import com.biblioteca.backend.service.AuthorService;
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

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthorService authorService;

    private Author exampleAuthor;

    @BeforeEach
    void setUp() {
        exampleAuthor = new Author();
        exampleAuthor.setId(1L);
        exampleAuthor.setName("Miguel");
        exampleAuthor.setSurname("de Cervantes");
        exampleAuthor.setNationality("Española");
        exampleAuthor.setBirthYear(1547);
        exampleAuthor.setAlive(false);
        exampleAuthor.setImage("https://link-a-imagen.com/cervantes.jpg");
    }

    @Test
    void getAllAuthors() throws Exception{
        when(authorService.getAll()).thenReturn(List.of(exampleAuthor));

        mockMvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Miguel"))
                .andExpect(jsonPath("$[0].surname").value("de Cervantes"));
    }

    @Test
    void getAuthorById() throws Exception {
        when(authorService.findAuthor(1L)).thenReturn(Optional.ofNullable(exampleAuthor));

        mockMvc.perform(get("/authors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Miguel"))
                .andExpect(jsonPath("$.surname").value("de Cervantes"));
    }
    @Test
    void createAuthor() throws Exception {
        when(authorService.addAuthor(any(Author.class))).thenReturn(exampleAuthor);

        String authorJson = "{\"name\":\"Miguel\", \"surname\":\"de Cervantes\", \"nationality\":\"Española\", \"birthYear\":1547, \"alive\":false}";

        mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authorJson))
                .andExpect(status().isCreated());
    }

    @Test
    void updateAuthor() throws Exception {
        exampleAuthor.setSurname("Cervantes Actualizado");

        when(authorService.findAuthor(1L)).thenReturn(Optional.of(exampleAuthor));

        when(authorService.updateAuthor(eq(1L), any(Author.class))).thenReturn(exampleAuthor);

        String authorJson = "{\"name\":\"Miguel\", \"surname\":\"Cervantes Actualizado\", \"nationality\":\"Española\", \"birthYear\":1547, \"alive\":false}";

        mockMvc.perform(put("/authors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authorJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.surname").value("Cervantes Actualizado"));
    }

    @Test
    void deleteAuthor() throws Exception {
        when(authorService.findAuthor(1L)).thenReturn(Optional.of(exampleAuthor));

        mockMvc.perform(delete("/authors/1"))
                .andExpect(status().isNoContent());
    }
}