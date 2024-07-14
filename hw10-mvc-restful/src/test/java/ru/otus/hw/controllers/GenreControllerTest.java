package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.GenreCreateDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.GenreService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreController.class)
class GenreControllerTest {



    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreService genreService;

    @Autowired
    private ObjectMapper mapper;

    @DisplayName("GenreController : Method(save())")
    @Test
    void shouldHaveSaveWithMethod() throws Exception {

        GenreDto genreDto = new GenreDto("1", "New Genre");

        given(genreService.create("New Genre")).willReturn(genreDto);

        mvc.perform(post("/api/genres/create").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new GenreCreateDto("New Genre"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Genre"))
                .andDo(print());
    }
}