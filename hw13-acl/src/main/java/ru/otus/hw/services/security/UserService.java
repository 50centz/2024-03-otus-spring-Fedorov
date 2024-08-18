package ru.otus.hw.services.security;

import ru.otus.hw.dto.security.UserCreateDto;

public interface UserService {

    void create(UserCreateDto userCreateDto);
}
