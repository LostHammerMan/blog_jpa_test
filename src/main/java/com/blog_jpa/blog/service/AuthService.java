package com.blog_jpa.blog.service;

import com.blog_jpa.blog.domain.entity.Session;
import com.blog_jpa.blog.domain.entity.User;
import com.blog_jpa.blog.dto.request.Login;
import com.blog_jpa.blog.dto.request.SignUpDto;
import com.blog_jpa.blog.exception.AlreadyExistUserException;
import com.blog_jpa.blog.exception.InvalidRequest;
import com.blog_jpa.blog.exception.InvalidSigningInformation;
import com.blog_jpa.blog.exception.Unauthorized;
import com.blog_jpa.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public Long signIn(Login login) {

        // DB 에서 유저정보 확인
        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(() -> new InvalidSigningInformation());
        log.info("user1 = {}", user.toString());

        // 세션 발급
        Session session = user.addSession();

        return user.getId();
    }

    @Transactional
    public void signUp(SignUpDto signUpDto) {

        // 중복 유저 여부 확인
        Optional<User> findUser = userRepository.findByEmail(signUpDto.getEmail());

        if (findUser.isPresent()){
            throw new AlreadyExistUserException();
        }

        SCryptPasswordEncoder encoder = new SCryptPasswordEncoder(16,
                8,
                1,
                32,
                64);
        String encryptPassword = encoder.encode(signUpDto.getPassword());

        // 엔티티로 변환
        User user = User.builder()
                .name(signUpDto.getName())
                .password(encryptPassword)
                .email(signUpDto.getEmail())
                .build();

        userRepository.save(user);
    }
}
