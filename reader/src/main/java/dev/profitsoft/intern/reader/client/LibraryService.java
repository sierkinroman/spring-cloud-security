package dev.profitsoft.intern.reader.client;

import dev.profitsoft.intern.reader.dto.BookDetailsDto;
import dev.profitsoft.intern.reader.dto.BookInfoDto;
import dev.profitsoft.intern.reader.dto.IdName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private final LibraryClient libraryClient;

    public IdName login() {
        return libraryClient.login();
    }

    public BookDetailsDto getBook(long bookId) {
        return libraryClient.getBook(bookId);
    }

    public Set<BookInfoDto> getPurchasedBooks(long userId) {
        return libraryClient.getPurchasedBooks(userId);
    }

}
