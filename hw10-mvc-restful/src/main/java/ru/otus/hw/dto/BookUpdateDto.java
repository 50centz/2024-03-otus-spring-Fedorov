package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
@EqualsAndHashCode(of = {"id"})
public class BookUpdateDto {

    @NotEmpty(message = "Id may not be null")
    private String id;

    @NotBlank(message = "Name field should not be blank")
    @Size(min = 2, max = 50, message = "Name field should has expected size")
    private String title;

    @NotEmpty(message = "Genre Id may not be null")
    private String genreId;

    @NotEmpty(message = "Collection authors may not be null")
    private Set<String> authorIds;
}
