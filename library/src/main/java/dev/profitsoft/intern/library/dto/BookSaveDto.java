package dev.profitsoft.intern.library.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class BookSaveDto {

    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "isbn is required")
    private String isbn;

    @NotBlank(message = "text is required")
    private String text;

}
