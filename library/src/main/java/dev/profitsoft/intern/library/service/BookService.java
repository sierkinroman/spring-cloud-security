package dev.profitsoft.intern.library.service;


import dev.profitsoft.intern.library.dto.BookDetailsDto;
import dev.profitsoft.intern.library.dto.BookInfoDto;
import dev.profitsoft.intern.library.dto.BookSaveDto;
import dev.profitsoft.intern.library.exception.NotFoundException;
import dev.profitsoft.intern.library.model.Book;
import dev.profitsoft.intern.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public Book findById(long id) {
        return getOrThrow(id);
    }

    @Transactional
    public long save(BookSaveDto dto) {
        Book book = new Book();
        updateBookFromDto(book, dto);
        return bookRepository.save(book).getId();
    }

    @Transactional(readOnly = true)
    public BookDetailsDto getDetailedBook(long id) {
        Book book = getOrThrow(id);
        return convertToBookDetails(book);
    }

    private void updateBookFromDto(Book book, BookSaveDto dto) {
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setText(dto.getText());
    }

    public BookInfoDto convertToBookInfo(Book book) {
        return BookInfoDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .build();
    }

    public BookDetailsDto convertToBookDetails(Book book) {
        return BookDetailsDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .text(book.getText())
                .build();
    }

    private Book getOrThrow(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book with id '%d' not found".formatted(id)));
    }

}
