package ru.otus.hw.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentService commentService;

    @MockBean
    private BookService bookService;

    private List<CommentDto> commentDtoList;
    private List<GenreDto> genreDtoList;
    private List<AuthorDto> authorDtoList;
    private List<BookDto> bookDtoList;
    private BookDto bookDto;

    @BeforeEach
    void setUp() {
        genreDtoList = getDbGenresDto();
        authorDtoList = getDbAuthorsDto();
        bookDtoList = getDbBooksDto();
        commentDtoList = getDbCommentsDto();
        bookDto = new BookDto("1", "BookTitle_1", genreDtoList.get(0), authorDtoList.stream().limit(1).toList());

    }

    @DisplayName("CommentController : Method(comments())")
    @Test
    void shouldHaveAllByBookIdWithMethod() throws Exception {
        given(commentService.findAllByBookId("1")).willReturn(commentDtoList);
        given(bookService.findById("1")).willReturn(bookDto);

        mvc.perform(get("/comments").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("comments"))
                .andExpect(model().attribute("commentDtoList", commentDtoList))
                .andExpect(model().attribute("bookDto", bookDto));
    }

    @DisplayName("CommentController : Method(deleteById())")
    @Test
    void shouldHaveDeleteByIdWithMethod() throws Exception {
        mvc.perform(get("/comment/delete").param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/comments?id="));
    }

    @DisplayName("CommentController : Method(commentSave())")
    @Test
    void shouldHaveReturnSavePageWithMethod() throws Exception {
        given(bookService.findAll()).willReturn(bookDtoList);

        mvc.perform(get("/comment-create"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-comment"))
                .andExpect(model().attribute("bookDtoList", bookDtoList));
    }

    @DisplayName("CommentController : Method(save())")
    @Test
    void shouldHaveSaveWithMethod() throws Exception {
        mvc.perform(post("/comment-create")
                        .param("comment", "New Comment")
                        .param("bookId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
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