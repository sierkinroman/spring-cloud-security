package dev.profitsoft.intern.library.controller;

import dev.profitsoft.intern.library.dto.BookDetailsDto;
import dev.profitsoft.intern.library.dto.BookSaveDto;
import dev.profitsoft.intern.library.dto.RestResponse;
import dev.profitsoft.intern.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('PRIV_BOOK_MANAGEMENT')")
    public RestResponse addBook(@Valid @RequestBody BookSaveDto dto) {
        long savedId = bookService.save(dto);
        return new RestResponse(String.valueOf(savedId));
    }

    @GetMapping("/{bookId}")
    public BookDetailsDto getBook(@PathVariable long bookId) {
        return bookService.getDetailedBook(bookId);
    }
}
