package ru.otus.hw.dto.security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {

    @NotBlank(message = "Username field should not be blank")
    @Size(min = 8, max = 50, message = "Username field should has minimum of 8 characters")
    private String username;

    @NotBlank(message = "Password field should not be blank")
    @Size(min = 8, max = 50, message = "Password field should has minimum of 8 characters")
    private String password;
}
