package ru.otus.hw.dto.security;

import org.springframework.stereotype.Component;
import ru.otus.hw.models.User;


@Component
public class UserMapper {

    public User toModel(UserCreateDto userCreateDto) {
        return new User(null, userCreateDto.getUsername(), userCreateDto.getPassword(), "USER");
    }
}
