package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.BookDto;

import java.util.List;

@RequiredArgsConstructor
@Component
public class BookConverter {
    private final AuthorConverter authorConverter;

    private final GenreConverter genreConverter;

    public String bookToString(BookDto bookDto) {
        return "Id: %s, title: %s, genres: [%s], authors: {%s}".formatted(
                bookDto.getId(),
                bookDto.getTitle(),
                genreConverter.genreToString(bookDto.getGenreDto()),
                convertAuthorsDtoToString(bookDto));
    }

    private String convertAuthorsDtoToString(BookDto bookDto) {
        List<String> authorDtoList = bookDto.getAuthorsDto().stream().map(authorConverter::authorToString).toList();

        StringBuilder stringBuilder = new StringBuilder();

        for (String s : authorDtoList) {
            stringBuilder.append(s).append(", ");
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return stringBuilder.toString();
    }
}
