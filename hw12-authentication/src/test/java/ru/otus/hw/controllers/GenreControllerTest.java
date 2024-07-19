package ru.otus.hw.controllers;

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
import ru.otus.hw.services.GenreService;
import ru.otus.hw.services.security.CustomUserDetailService;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(GenreController.class)
@Import({SecurityConfiguration.class, AppConfig.class})
class GenreControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreService genreService;

    @MockBean
    private CustomUserDetailService customUserDetailService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @DisplayName("GenreController : Method(create() WithUsername)")
    @Test
    void testAuthenticatedWithUsernameWithMethodCreate() throws Exception {
        mvc.perform(get("/genre-create")
                        .with(user("username").authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(view().name("create-genre"));
    }

    @DisplayName("GenreController : Method(create() WithNotUsername)")
    @Test
    void testAuthenticatedWithNotUsernameWithMethodCreate() throws Exception {
        mvc.perform(get("/genre-create"))
                .andExpect(redirectedUrl("http://localhost/login"))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("GenreController : Method(save() WithUsername)")
    @Test
    void testAuthenticatedWithUsernameWithMethodSave() throws Exception {

        mvc.perform(post("/genre-create")
                        .param("name", "New Genre")
                        .with(user("username").authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @DisplayName("GenreController : Method(save() WithNotUsername)")
    @Test
    void testAuthenticatedWithNotUsernameWithMethodSave() throws Exception {

        mvc.perform(post("/genre-create")
                        .param("name", "New Genre"))
                .andExpect(redirectedUrl("http://localhost/login"))
                .andExpect(status().is3xxRedirection());
    }
}