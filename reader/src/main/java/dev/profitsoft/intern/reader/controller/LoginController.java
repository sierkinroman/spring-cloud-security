package dev.profitsoft.intern.reader.controller;

import dev.profitsoft.intern.reader.client.AuthToken;
import dev.profitsoft.intern.reader.client.LibraryService;
import dev.profitsoft.intern.reader.dto.IdName;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LibraryService libraryService;

    private final AuthToken authToken;

    @GetMapping
    public String showLoginPage() {
        return "login";
    }

    @PostMapping
    public String login(Model model,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletRequest request,
                        HttpServletResponse response) {
        String token = Base64.getEncoder().encodeToString("%s:%s".formatted(username, password).getBytes());
        response.addHeader("authorization", "Basic " + token);
        try {
            IdName idName = libraryService.login();
            authToken.setAuthToken(token);
            request.getSession().setAttribute("authIdName", idName);
            return "redirect:/";
        } catch (FeignException.Unauthorized e) {
            model.addAttribute("error", true);
            return "login";
        }
    }

}
