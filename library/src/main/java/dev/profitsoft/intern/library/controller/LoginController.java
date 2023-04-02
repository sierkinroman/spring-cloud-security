package dev.profitsoft.intern.library.controller;

import dev.profitsoft.intern.library.dto.IdName;
import dev.profitsoft.intern.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping
    public IdName getIdName(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        return new IdName(userService.getIdByUsername(user.getUsername()), user.getUsername());
    }

}
