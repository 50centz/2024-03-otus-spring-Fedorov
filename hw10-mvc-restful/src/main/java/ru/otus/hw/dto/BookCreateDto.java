package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
public class BookCreateDto {

    @NotBlank(message = "Name field should not be blank")
    @Size(min = 2, max = 50, message = "Name field should has expected size")
    private String title;

    private String genreId;

    private Set<String> authorIds;
}