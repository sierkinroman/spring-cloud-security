package dev.profitsoft.intern.reader.client;

import dev.profitsoft.intern.reader.dto.BookDetailsDto;
import dev.profitsoft.intern.reader.dto.BookInfoDto;
import dev.profitsoft.intern.reader.dto.IdName;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@FeignClient(name = "library", decode404 = true)
public interface LibraryClient {

    @GetMapping("/login")
    IdName login();

    @GetMapping("/api/books/{id}")
    BookDetailsDto getBook(@PathVariable long id);

    @GetMapping("/api/users/{userId}/books")
    Set<BookInfoDto> getPurchasedBooks(@PathVariable long userId);

}
