package dev.profitsoft.intern.reader.controller;

import dev.profitsoft.intern.reader.client.AuthToken;
import dev.profitsoft.intern.reader.client.LibraryService;
import dev.profitsoft.intern.reader.dto.BookInfoDto;
import dev.profitsoft.intern.reader.dto.IdName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final RestTemplate restTemplate;

    private final LibraryService libraryService;

    private final AuthToken authToken;

//    public static final Duration REST_TEMPLATE_TIMEOUT = Duration.ofSeconds(30);
//
//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
//        return restTemplateBuilder
//                .setConnectTimeout(REST_TEMPLATE_TIMEOUT)
//                .setReadTimeout(REST_TEMPLATE_TIMEOUT)
//                .build();
//    }
//
////        HttpHeaders headers = new HttpHeaders();
////        headers.set("Authorization", "Basic YWRtaW46YWRtaW4=");
////        HttpEntity<BookDetailsDto> entity = new HttpEntity<>(headers);
////        ResponseEntity<BookDetailsDto> response = restTemplate.exchange("http://localhost:8080/api/books/1", HttpMethod.GET, entity, BookDetailsDto.class);

    @GetMapping
    public String showHomepage(Model model, HttpServletRequest request, HttpServletResponse response) {
        if (authToken.getAuthToken() != null && !authToken.getAuthToken().isEmpty()) {
            IdName authIdName = (IdName) request.getSession().getAttribute("authIdName");
            response.addHeader("authorization", "Basic " + authToken.getAuthToken());
            Set<BookInfoDto> purchasedBooks = libraryService.getPurchasedBooks(authIdName.getId());
            model.addAttribute("purchasedBooks", purchasedBooks);
            model.addAttribute("authIdName", authIdName);
        } else {
            model.addAttribute("isNeedLogin", true);
        }
        return "home";
    }

    @GetMapping("/books/{id}")
    public String showBook(@PathVariable long id, Model model, HttpServletRequest request, HttpServletResponse response) {
        response.addHeader("authorization", "Basic " + authToken.getAuthToken());
        model.addAttribute("bookDetails", libraryService.getBook(id));
        return "book";
    }

}
