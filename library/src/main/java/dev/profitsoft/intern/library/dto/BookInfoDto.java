package dev.profitsoft.intern.library.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookInfoDto {

    private long id;

    private String title;

    private String isbn;
}
