package ru.otus.hw.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.support.DelegatingMessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class AppPropertiesTest {

    private AppProperties appProperties;


    @BeforeEach
    public void setUp() {
        appProperties = Mockito.mock(AppProperties.class);
        Mockito.when(appProperties.getLocale()).thenReturn(new Locale("en-US"));
        Mockito.when(appProperties.getRightAnswersCountToPass()).thenReturn(3);
    }



    @DisplayName("AppProperties : Method(getRightAnswersCountToPass)")
    @Test
    void shouldHaveAnswersCountWithMethod() {
        int result = 3;
        assertEquals(result, appProperties.getRightAnswersCountToPass());
    }

    @DisplayName("AppProperties : Method(getLocale)")
    @Test
    void shouldHaveReturnLocaleWithMethod() {
        Locale locale = new Locale("en-US");
        assertEquals(locale, appProperties.getLocale());
    }
}