package com.blog_jpa.blog.config.handler;

import com.blog_jpa.blog.dto.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
public class LoginFailHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
      log.error("[인증 오류] 아이디 혹은 비밀번호가 올바르지 않습니다");

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message("아이디 혹은 비밀번호가 올바르지 않습니다")
                .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        // 객체 -> json
      String json = objectMapper.writeValueAsString(errorResponse);

      // 방법 1
      objectMapper.writeValue(response.getWriter(), errorResponse);

      // 방법 2
//      response.getWriter().write(json);
    }
}
