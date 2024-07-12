package ru.otus.hw.services;

import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Author;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CheckingResponseFromDbImpl implements CheckingResponseFromDb {
    @Override
    public void isEmpty(List<Author> authors, Set<String> authorIds) {
        if (authors.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();

            for (String id : authorIds) {
                stringBuilder.append(id).append(", ");
            }

            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);

            if (authorIds.size() == 1) {
                throw new NotFoundException("Author with id: " + stringBuilder + " not found");
            }

            throw new NotFoundException("Authors with ids: " + stringBuilder + " not found");
        }
    }

    @Override
    public void checkAuthors(List<Author> authors, Set<String> authorIds) {
        if (authors.size() < authorIds.size()) {
            Set<String> res = authors.stream().map(Author::getId).collect(Collectors.toSet());
            Set<String> result = authorIds.stream().filter(id -> !res.contains(id)).collect(Collectors.toSet());

            if (result.size() == 1) {
                throw new NotFoundException("Author with id: " + result.toString() + " not found");
            }

            StringBuilder stringBuilder = new StringBuilder();

            for (String id : result) {
                stringBuilder.append(id).append(", ");
            }

            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);

            throw new NotFoundException("Authors with ids: " + stringBuilder + " not found");
        }
    }
}
