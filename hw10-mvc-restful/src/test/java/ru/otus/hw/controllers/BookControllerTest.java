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
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.services.BookService;

import java.util.List;
import java.util.Set;
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

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper mapper;


    private List<GenreDto> genreDtoList;
    private List<AuthorDto> authorDtoList;
    private List<BookDto> bookDtoList;
    private BookDto bookDto;

    @BeforeEach
    void setUp() {
        genreDtoList = getDbGenresDto();
        authorDtoList = getDbAuthorsDto();
        bookDtoList = getDbBooksDto();
        bookDto = createBookDto();
    }

    @DisplayName("BookController : Method(generalPage())")
    @Test
    void shouldHaveReturnGeneralPageWithMethod() throws Exception {
        given(bookService.findAll()).willReturn(bookDtoList);

        mvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(3))
                .andDo(print());
    }

    @DisplayName("BookController : Method(deleteById())")
    @Test
    void shouldHaveDeleteByIdWithMethod() throws Exception {
        mvc.perform(delete("/api/books/delete/1"))
                .andExpect(status().isNoContent());
    }

    @DisplayName("BookController : Method(save())")
    @Test
    void shouldHaveSaveWithMethod() throws Exception {

        BookCreateDto bookCreateDto = new BookCreateDto("New Book", "1", Set.of("1"));

        given(bookService.create(bookCreateDto)).willReturn(bookDto);

        mvc.perform(post("/api/books/create").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(bookCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("New Book"))
                .andExpect(jsonPath("$.authorsDto.[0].id").value("1"))
                .andExpect(jsonPath("$.authorsDto.[0].fullName").value("Author_1"))
                .andDo(print());
    }

    @DisplayName("BookController : Method(update())")
    @Test
    void shouldHaveUpdateWithMethod() throws Exception {
        BookUpdateDto bookUpdateDto = new BookUpdateDto("1","New Book Update", "1", Set.of("1"));

        BookDto bookDtoNew = new BookDto("1", "New Book", new GenreDto("1", "New Genre"),
                List.of(new AuthorDto("1", "New Author")));

        given(bookService.update(bookUpdateDto)).willReturn(bookDtoNew);

        mvc.perform(post("/api/books/edit").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(bookUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("New Book"))
                .andExpect(jsonPath("$.genreDto.id").value("1"))
                .andExpect(jsonPath("$.genreDto.name").value("New Genre"))
                .andDo(print());

    }


    @DisplayName("Exception 404")
    @Test
    void shouldHaveException404WithMethod() throws Exception {

//        given(bookService.findAll()).willThrow(new NotFoundException("Not Found Exception 404"));

        mvc.perform(get("/api/books/exception"))
                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.errorMessage").value("Not Found Exception 404"))
                .andDo(print());

    }

    @DisplayName("Exception 500")
    @Test
    void shouldHaveException500WithMethod() throws Exception {
        given(bookService.findAll()).willThrow(new RuntimeException("Exception 500"));

        mvc.perform(get("/api/books"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errorMessage").value("Exception 500"))
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

    private BookDto createBookDto() {
        return new BookDto("1", "New Book", genreDtoList.get(0), authorDtoList.stream()
                .limit(1).toList());
    }
}