package com.blog_jpa.blog.service;

import com.blog_jpa.blog.domain.entity.User;
import com.blog_jpa.blog.dto.request.Login;
import com.blog_jpa.blog.dto.request.SignUpDto;
import com.blog_jpa.blog.exception.AlreadyExistUserException;
import com.blog_jpa.blog.exception.InvalidSigningInformation;
import com.blog_jpa.blog.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class AuthServiceTest {

    @Autowired
    AuthService authService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    public void clean(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 성공")
    void test0(){

        // given
//        PasswordEncoder passwordEncoder = new PasswordEncoder();
//        String encryptedPassword = passwordEncoder.encrypt("1234");

//        User user = User.builder()
//                .email("111@111")
//                .name("은강선")
//                .password(encryptedPassword)
//                .build();

//        userRepository.save(user);


        // authService 의 코드가 달라지면 영향을 받으므로 userRepository 로 접근


        Login login = Login.builder()
                .email("111@111")
                .password("1234")
                .build();


        // when
        Long userId = authService.signIn(login);

        // then
//        Assertions.assertEquals(1, userRepository.count());
//        Assertions.assertEquals("금강선", userRepository.findAll().get(0).getName());
//        Assertions.assertEquals("111@111", userRepository.findAll().get(0).getEmail());
//        Assertions.assertNotEquals("1234", userRepository.findAll().get(0).getPassword());
        Assertions.assertNotNull(userId);
//        Assertions.assertTrue(passwordEncoder.matches(login.getPassword(), user.getPassword()));

    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호 틀림")
    void test0_1(){

        // given
        SignUpDto signUpDto = SignUpDto.builder()
                .name("금강선")
                .password("1234")
                .email("111@111").build();

        authService.signUp(signUpDto);

        Login login = Login.builder()
                .email("111@111")
                .password("5678")
                .build();


        // then
        Assertions.assertThrows(InvalidSigningInformation.class, () ->authService.signIn(login));

    }


    @Test
    @DisplayName("회원 가입 성공")
    void test1(){

//        PasswordEncoder passwordEncoder = new PasswordEncoder();
        // given
        SignUpDto signUpDto = SignUpDto.builder()
                .name("금강선")
                .password("1234")
                .email("111@111").build();

        // when
        authService.signUp(signUpDto);

        User findUser = userRepository.findAll().get(0);

        // then
        Assertions.assertEquals(1, userRepository.count());
        Assertions.assertEquals("금강선", userRepository.findAll().get(0).getName());
        Assertions.assertEquals("111@111", userRepository.findAll().get(0).getEmail());
        Assertions.assertTrue(passwordEncoder.matches("1234", findUser.getPassword()));
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