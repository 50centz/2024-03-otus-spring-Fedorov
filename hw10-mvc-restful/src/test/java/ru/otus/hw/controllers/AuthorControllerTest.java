package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.AuthorCreateDto;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.services.AuthorService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @Autowired
    private ObjectMapper mapper;

    @DisplayName("AuthorController : Method(save())")
    @Test
    void shouldHaveSaveWithMethod() throws Exception {

        AuthorDto authorDto = new AuthorDto("1", "New Author");

        given(authorService.create("New Author")).willReturn(authorDto);

        mvc.perform(post("/api/authors/create").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new AuthorCreateDto("New Author"))))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fullName").value("New Author"))
                .andDo(print());
    }

}