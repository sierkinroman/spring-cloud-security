package dev.profitsoft.intern.library.controller;

import dev.profitsoft.intern.library.dto.BookInfoDto;
import dev.profitsoft.intern.library.dto.RestResponse;
import dev.profitsoft.intern.library.dto.UserSaveDto;
import dev.profitsoft.intern.library.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('PRIV_USER_MANAGEMENT')")
    public RestResponse addUser(@Valid @RequestBody UserSaveDto dto) {
        long savedId = userService.save(dto);
        return new RestResponse(String.valueOf(savedId));
    }

    @PostMapping("/{userId}/books")
    @PreAuthorize("hasAuthority('PRIV_USER_MANAGEMENT')")
    public RestResponse addBookToUser(@PathVariable long userId, @RequestParam(name = "bookId") long bookId) {
        userService.addBookToUser(userId, bookId);
        return new RestResponse("Ok");
    }

    @GetMapping("/{userId}/books")
    public Set<BookInfoDto> getPurchasedBooks(@PathVariable long userId) {
        return userService.getBooks(userId);
    }

}
