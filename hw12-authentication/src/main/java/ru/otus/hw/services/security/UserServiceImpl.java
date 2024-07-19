package ru.otus.hw.services.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.hw.dto.security.UserCreateDto;
import ru.otus.hw.dto.security.UserMapper;
import ru.otus.hw.models.User;
import ru.otus.hw.repositories.security.UserRepository;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Override
    public void create(UserCreateDto userCreateDto) {
        User user = userMapper.toModel(userCreateDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
