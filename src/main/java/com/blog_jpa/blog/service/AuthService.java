package com.blog_jpa.blog.service;

import com.blog_jpa.blog.domain.entity.Session;
import com.blog_jpa.blog.domain.entity.User;
import com.blog_jpa.blog.dto.request.Login;
import com.blog_jpa.blog.exception.InvalidSigningInformation;
import com.blog_jpa.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public String signIn(Login login) {

        // DB 에서 유저정보 확인
        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(() -> new InvalidSigningInformation());
        log.info("user1 = {}", user.toString());

        // 세션 발급
        Session session = user.addSession();

        return session.getAccessToken();



    }
}
