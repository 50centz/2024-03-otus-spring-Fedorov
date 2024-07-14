package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateDto {

    @NotBlank(message = "Name field should not be blank")
    @Size(min = 2, max = 50, message = "Name field should has expected size")
    private String comment;

    private String bookId;
}
