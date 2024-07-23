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
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.security.CustomUserDetailService;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(AuthorController.class)
@Import({SecurityConfiguration.class, AppConfig.class})
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private CustomUserDetailService customUserDetailService;

    @MockBean
    private PasswordEncoder passwordEncoder;


    @DisplayName("AuthorController : Method(create() WithNotUsername)")
    @Test
    void testAuthenticatedWithNotUsername() throws Exception {
        mvc.perform(get("/author-create"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"))
                .andDo(print());
    }

    @DisplayName("AuthorController : Method(create() WithUsername)")
    @Test
    void testAuthenticatedWithUsername() throws Exception {
        mvc.perform(get("/author-create").with(user("username").authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @DisplayName("AuthorController : Method(save() WithNotUsername)")
    @Test
    void testAuthenticatedWithNotUsernameWithMethodSave() throws Exception {
        mvc.perform(post("/author-create")
                .param("fullName", "New Author"))
                .andExpect(redirectedUrl("http://localhost/login"))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("AuthorController : Method(save() WithUsername)")
    @Test
    void testAuthenticatedWithUsernameWithMethodSave() throws Exception {
        mvc.perform(post("/author-create")
                        .param("fullName", "New Author")
                        .with(user("username").authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                .andExpect(status().is3xxRedirection());
    }
}