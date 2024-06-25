package ru.otus.hw.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.services.GenreService;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(GenreController.class)
class GenreControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreService genreService;

    @DisplayName("GenreController : Method(create())")
    @Test
    void shouldHaveReturnPageWithMethod() throws Exception {
        mvc.perform(get("/genre-create"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-genre"));
    }

    @DisplayName("GenreController : Method(save())")
    @Test
    void shouldHaveSaveWithMethod() throws Exception {

        mvc.perform(post("/genre-create")
                        .param("name", "New Genre"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }
}