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
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentServiceImpl;
import ru.otus.hw.services.GenreService;

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

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private CommentServiceImpl commentService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private AuthorService authorService;

    private List<GenreDto> genreDtoList;
    private List<AuthorDto> authorDtoList;
    private List<BookDto> bookDtoList;
    private BookDto bookDto;

    @BeforeEach
    void setUp() {
        genreDtoList = getDbGenresDto();
        authorDtoList = getDbAuthorsDto();
        bookDtoList = getDbBooksDto();
        bookDto = new BookDto("1", "BookTitle_1", genreDtoList.get(0), authorDtoList.stream().limit(1).toList());
    }

    @DisplayName("BookController : Method(generalPage())")
    @Test
    void shouldHaveReturnGeneralPageWithMethod() throws Exception {
        given(bookService.findAll()).willReturn(bookDtoList);

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("bookDtoList", bookDtoList));
    }

    @DisplayName("BookController : Method(deleteById())")
    @Test
    void shouldHaveDeleteByIdWithMethod() throws Exception {
        mvc.perform(get("/book/delete").param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @DisplayName("BookController : Method(create())")
    @Test
    void shouldHaveCreateWithMethod() throws Exception {
        given(genreService.findAll()).willReturn(genreDtoList);
        given(authorService.findAll()).willReturn(authorDtoList);

        mvc.perform(get("/book-create"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-book"))
                .andExpect(model().attribute("genreDtoList", genreDtoList))
                .andExpect(model().attribute("authorDtoList", authorDtoList));
    }

    @DisplayName("BookController : Method(save())")
    @Test
    void shouldHaveSaveWithMethod() throws Exception {

        mvc.perform(post("/book-create")
                        .param("title", "New Title")
                        .param("genre", "New Genre")
                        .param("author", "New Author"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @DisplayName("BookController : Method(editPage())")
    @Test
    void shouldHaveReturnEditPageWithMethod() throws Exception {
        given(genreService.findAll()).willReturn(genreDtoList);
        given(authorService.findAll()).willReturn(authorDtoList);
        given(bookService.findById("1")).willReturn(bookDto);


        mvc.perform(get("/edit")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"))
                .andExpect(model().attribute("bookDto", bookDto))
                .andExpect(model().attribute("genreDtoList", genreDtoList))
                .andExpect(model().attribute("authorDtoList", authorDtoList));

    }

    @DisplayName("BookController : Method(update())")
    @Test
    void shouldHaveUpdateWithMethod() throws Exception {
        mvc.perform(post("/edit")
                        .param("bookId", "1")
                        .param("title", "New Title")
                        .param("genre", "New Genre")
                        .param("author", "New Author"))
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