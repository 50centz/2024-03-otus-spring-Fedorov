package ru.otus.hw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw.services.CheckingResponseFromDb;
import ru.otus.hw.services.CheckingResponseFromDbImpl;

@Configuration
public class CheckingResponseFromDbConfiguration {

    @Bean
    public CheckingResponseFromDb getBean() {
        return new CheckingResponseFromDbImpl();
    }
}
