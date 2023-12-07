package com.blog_jpa.blog.controller;

import com.blog_jpa.blog.domain.entity.User;
import com.blog_jpa.blog.dto.request.Login;
import com.blog_jpa.blog.dto.response.SessionResponse;
import com.blog_jpa.blog.exception.InvalidRequest;
import com.blog_jpa.blog.exception.InvalidSigningInformation;
import com.blog_jpa.blog.repository.UserRepository;
import com.blog_jpa.blog.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

// id, pwd 를 받아 토큰 발급
@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

//    private final UserRepository userRepository;
        private final AuthService authService;
    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login){

        // json 아이디, 비번
        log.info("\t login = {}", login);
        String accessToken = authService.signIn(login);
        return new SessionResponse(accessToken);
        // 토큰 응답
    }
}
