package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        // Использовать CsvToBean
        // https://opencsv.sourceforge.net/#collection_based_bean_fields_one_to_many_mappings
        // Использовать QuestionReadException
        // Про ресурсы: https://mkyong.com/java/java-read-a-file-from-resources-folder/

        List<QuestionDto> questionDtoList = getQuestionDto();
        List<Question> questionList = new ArrayList<>();
        questionList = questionDtoList.stream().map(QuestionDto::toDomainObject).toList();
        return questionList;
    }

    private List<QuestionDto> getQuestionDto() {
        InputStream inputStream = getInputStream();
        return getListQuestionDto(inputStream);
    }

    private InputStream getInputStream(){
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileNameProvider.getTestFileName());
        if (inputStream == null) {
            throw new IllegalArgumentException("File not found! " + fileNameProvider.getTestFileName());
        }
        return inputStream;
    }

    private List<QuestionDto> getListQuestionDto(InputStream inputStream){
        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {
            return new CsvToBeanBuilder<QuestionDto>(inputStreamReader)
                    .withType(QuestionDto.class)
                    .withSkipLines(1)
                    .withSeparator(';')
                    .build()
                    .parse();
        } catch (IOException e) {
            throw new QuestionReadException("Filed to read file", e);
        }
    }
}
