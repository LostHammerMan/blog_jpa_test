package com.blog_jpa.blog.service;

import com.blog_jpa.blog.domain.entity.User;
import com.blog_jpa.blog.dto.request.SignUpDto;
import com.blog_jpa.blog.exception.AlreadyExistUserException;
import com.blog_jpa.blog.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class AuthServiceTest {

    @Autowired
    AuthService authService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void clean(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 가입 성공")
    void test1(){

        // given
        SignUpDto signUpDto = SignUpDto.builder()
                .name("금강선")
                .password("1234")
                .email("111@111").build();

        // when
        authService.signUp(signUpDto);

        // then
        Assertions.assertEquals(1, userRepository.count());
        Assertions.assertEquals("금강선", userRepository.findAll().get(0).getName());
        Assertions.assertEquals("111@111", userRepository.findAll().get(0).getEmail());
        Assertions.assertNotEquals("1234", userRepository.findAll().get(0).getPassword());

    }

    @Test
    @DisplayName("회원 가입시 중복된 이메일")
    void test2() {

        // given
        User user = User.builder()
                .name("금강선")
                .password("1234")
                .email("111@111").build();

        userRepository.save(user);

        SignUpDto signUpDto2 = SignUpDto.builder()
                .name("금강선")
                .password("1234")
                .email("111@111").build();


        // expected
        Assertions.assertThrows(AlreadyExistUserException.class,
                () -> authService.signUp(signUpDto2));
    }

    @Test
    @DisplayName("비밀번호 암호화")
    void test3(){

        //
    }
}