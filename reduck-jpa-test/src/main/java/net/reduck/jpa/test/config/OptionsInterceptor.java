package net.reduck.jpa.test.config;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gin
 * @since 2023/4/27 14:50
 */
@Component
public class OptionsInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod())) {
            if (!response.containsHeader("Allow")) {
                response.setHeader("Allow", "GET,POST,OPTIONS");
            }
        }
    }
}
