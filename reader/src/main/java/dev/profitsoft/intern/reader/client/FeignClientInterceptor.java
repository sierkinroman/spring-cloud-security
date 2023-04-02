package dev.profitsoft.intern.reader.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Component
@AllArgsConstructor
public class FeignClientInterceptor implements RequestInterceptor {

    private final HttpServletResponse response;

    @Override
    public void apply(RequestTemplate template) {
        String reqAuthInput= response.getHeader("authorization");
        if (reqAuthInput!= null) {
            template.header("authorization", reqAuthInput);
        }
    }
}
