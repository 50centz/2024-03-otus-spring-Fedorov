package ru.otus.hw.controllers;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.config.security.AppConfig;
import ru.otus.hw.config.security.SecurityConfiguration;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentServiceImpl;
import ru.otus.hw.services.GenreService;
import ru.otus.hw.services.security.CustomUserDetailService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(BookController.class)
@Import({SecurityConfiguration.class, AppConfig.class})
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

    @MockBean
    private CustomUserDetailService customUserDetailService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private List<GenreDto> genreDtoList;
    private List<AuthorDto> authorDtoList;
    private List<BookDto> bookDtoList;
    private BookDto bookDto;

    @BeforeEach
    void setUp() {
        genreDtoList = getDbGenresDto();
        authorDtoList = getDbAuthorsDto();
        bookDtoList = getDbBooksDto();
        bookDto = new BookDto("1", "BookTitle_1", genreDtoList.get(0),
                authorDtoList.stream().limit(1).toList());
    }

    @DisplayName("BookController : Method(generalPage() WithUsername)")
    @Test
    void testAuthenticatedWithUsernameWithMethodGeneralPage() throws Exception {
        given(bookService.findAll()).willReturn(bookDtoList);

        mvc.perform(get("/")
                        .with(user("username").authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("bookDtoList", bookDtoList));
    }

    @DisplayName("BookController : Method(generalPage() WithNotUsername)")
    @Test
    void testAuthenticatedWithNotUsernameWithMethodGeneralPage() throws Exception {
        given(bookService.findAll()).willReturn(bookDtoList);

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("bookDtoList", bookDtoList));
    }

    @DisplayName("BookController : Method(deleteById() WithUsername)")
    @Test
    void testAuthenticatedWithUsernameWithMethodDeleteById() throws Exception {
        mvc.perform(post("/book/delete/1")
                        .with(user("username").authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @DisplayName("BookController : Method(deleteById() WithNotUsername)")
    @Test
    void testAuthenticatedWithNotUsernameWithMethodDeleteById() throws Exception {
        mvc.perform(post("/book/delete/1"))
                .andExpect(redirectedUrl("http://localhost/login"))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("BookController : Method(create() WithUsername)")
    @Test
    void testAuthenticatedWithUsernameWithMethodCreate() throws Exception {
        given(genreService.findAll()).willReturn(genreDtoList);
        given(authorService.findAll()).willReturn(authorDtoList);

        mvc.perform(get("/book-create")
                        .with(user("username").authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(view().name("create-book"))
                .andExpect(model().attribute("genreDtoList", genreDtoList))
                .andExpect(model().attribute("authorDtoList", authorDtoList));
    }

    @DisplayName("BookController : Method(create() WithNotUsername)")
    @Test
    void testAuthenticatedWithNotUsernameWithMethodCreate() throws Exception {
        mvc.perform(get("/book-create"))
                .andExpect(redirectedUrl("http://localhost/login"))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("BookController : Method(save() WithUsername)")
    @Test
    void testAuthenticatedWithUsernameWithMethodSave() throws Exception {

        mvc.perform(post("/book-create")
                        .param("title", "New Title")
                        .param("genre", "New Genre")
                        .param("author", "New Author")
                        .with(user("username").authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @DisplayName("BookController : Method(save() WithNotUsername)")
    @Test
    void testAuthenticatedWithNotUsernameWithMethodSave() throws Exception {

        mvc.perform(post("/book-create")
                        .param("title", "New Title")
                        .param("genre", "New Genre")
                        .param("author", "New Author"))
                .andExpect(redirectedUrl("http://localhost/login"))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("BookController : Method(editPage() WithUsername)")
    @Test
    void testAuthenticatedWithUsernameWithMethodEditPage() throws Exception {
        given(genreService.findAll()).willReturn(genreDtoList);
        given(authorService.findAll()).willReturn(authorDtoList);
        given(bookService.findById("1")).willReturn(bookDto);


        mvc.perform(get("/edit/1")
                        .with(user("username").authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"))
                .andExpect(model().attribute("bookDto", bookDto))
                .andExpect(model().attribute("genreDtoList", genreDtoList))
                .andExpect(model().attribute("authorDtoList", authorDtoList));

    }

    @DisplayName("BookController : Method(editPage() WithNotUsername)")
    @Test
    void testAuthenticatedWithNotUsernameWithMethodEditPage() throws Exception {
        given(genreService.findAll()).willReturn(genreDtoList);
        given(authorService.findAll()).willReturn(authorDtoList);
        given(bookService.findById("1")).willReturn(bookDto);


        mvc.perform(get("/edit/1"))
                .andExpect(redirectedUrl("http://localhost/login"))
                .andExpect(status().is3xxRedirection());

    }

    @DisplayName("BookController : Method(update() WithUsername)")
    @Test
    void testAuthenticatedWithUsernameWithMethodUpdate() throws Exception {
        mvc.perform(post("/edit")
                        .param("bookId", "1")
                        .param("title", "New Title")
                        .param("genre", "New Genre")
                        .param("author", "New Author")
                        .with(user("username").authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @DisplayName("BookController : Method(update() WithNotUsername)")
    @Test
    void testAuthenticatedWithNotUsernameWithMethodUpdate() throws Exception {
        mvc.perform(post("/edit")
                        .param("bookId", "1")
                        .param("title", "New Title")
                        .param("genre", "New Genre")
                        .param("author", "New Author"))
                .andExpect(redirectedUrl("http://localhost/login"))
                .andExpect(status().is3xxRedirection());
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