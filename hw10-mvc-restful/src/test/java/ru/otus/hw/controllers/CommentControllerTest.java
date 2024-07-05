package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.CommentCreateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.CommentService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CommentController.class)
class CommentControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentService commentService;

    @Autowired
    private ObjectMapper mapper;

    private List<CommentDto> commentDtoList;
    private List<GenreDto> genreDtoList;
    private List<AuthorDto> authorDtoList;
    private List<BookDto> bookDtoList;

    @BeforeEach
    void setUp() {
        genreDtoList = getDbGenresDto();
        authorDtoList = getDbAuthorsDto();
        bookDtoList = getDbBooksDto();
        commentDtoList = getDbCommentsDto();
    }

    @DisplayName("CommentController : Method(comments())")
    @Test
    void shouldHaveAllByBookIdWithMethod() throws Exception {

        given(commentService.findAllByBookId("1")).willReturn(commentDtoList);

        mvc.perform(get("/api/comments/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andDo(print());
    }

    @DisplayName("CommentController : Method(deleteById())")
    @Test
    void shouldHaveDeleteByIdWithMethod() throws Exception {
        mvc.perform(delete("/api/comments/delete/1"))
                .andExpect(status().isOk());
    }

    @DisplayName("CommentController : Method(commentSave())")
    @Test
    void shouldHaveReturnSavePageWithMethod() throws Exception {


        CommentDto commentDto = new CommentDto("1", "New Comment", bookDtoList.get(0));

        given(commentService.create(commentDto.getComment(), commentDto.getBookDto().getId())).willReturn(commentDto);

        mvc.perform(post("/api/comments/create").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new CommentCreateDto("New Comment", "1"))))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comment").value("New Comment"))
                .andDo(print());
    }

    private  List<GenreDto> getDbGenresDto() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new GenreDto(id.toString(), "Genre_" + id)).toList();
    }

    private  List<AuthorDto> getDbAuthorsDto() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new AuthorDto(id.toString(), "Author_" + id)).toList();
    }

    private  List<BookDto> getDbBooksDto() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new BookDto(id.toString(), "BookTitle_" + id, genreDtoList.get(id - 1),
                        authorDtoList.stream().skip(id - 1).limit(1).toList())).toList();
    }

    private  List<CommentDto> getDbCommentsDto() {
        List<CommentDto> list = new ArrayList<>();
        list.add(new CommentDto("1", "Comment 1", bookDtoList.get(0)));
        list.add(new CommentDto("4", "Comment 4", bookDtoList.get(0)));
        return list;
    }
}