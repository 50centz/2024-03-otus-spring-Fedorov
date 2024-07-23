package ru.otus.hw.controllers.security;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.hw.dto.security.UserCreateDto;
import ru.otus.hw.dto.security.UserLoginDto;
import ru.otus.hw.services.security.UserService;


@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        UserLoginDto userLoginDto = new UserLoginDto();
        model.addAttribute("userLoginDto", userLoginDto);
        return "security/login";
    }

    @PostMapping("/login")
    public String login(@Valid UserLoginDto userLoginDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "security/login";
        }

        return "redirect:/";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        UserCreateDto userCreateDto = new UserCreateDto();
        model.addAttribute("userCreateDto", userCreateDto);
        return  "security/registration";
    }

    @PostMapping("/registration")
    public String save(@Valid UserCreateDto userCreateDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "security/registration";
        }

        userService.create(userCreateDto);

        return "redirect:/";
    }
}
