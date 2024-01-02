package com.blog_jpa.blog.controller;

import com.blog_jpa.blog.domain.entity.User;
import com.blog_jpa.blog.dto.request.Login;
import com.blog_jpa.blog.dto.request.SignUpDto;
import com.blog_jpa.blog.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.session.SessionRepository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionRepository sessionRepository;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 성공")
    public void test() throws Exception {



        // given
        User sampleUser = User.builder()
                .email("333@333")
                .password("1234")
                .name("금강선")
                .build();


        userRepository.save(sampleUser);


        Login login = Login.builder()
                .email("333@333")
                .password("1234")
                .build();

        String loginJson = objectMapper.writeValueAsString(login);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Transactional // 테스트 복잡할 경우 오염 발생할 가능성 있음
    @DisplayName("로그인 성공 후 세션 1개 생성")
    public void test2() throws Exception {
        // given
        User sampleUser = User.builder()
                .email("333@333")
                .password("1234")
                .name("금강선")
                .build();


        userRepository.save(sampleUser);


        Login login = Login.builder()
                .email("333@333")
                .password("1234")
                .build();

        // 세션 생성

        String loginJson = objectMapper.writeValueAsString(login);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

//        User loggedInUser = userRepository.findById(sampleUser.getId())
//                .orElseThrow(() -> new RuntimeException());


//        Assertions.assertEquals(1L, sampleUser.getSessions().size());
    }

    @Test
    @DisplayName("로그인 성공 후 세션 응답")
    public void test3() throws Exception {
        // given
        User sampleUser = User.builder()
                .email("333@333")
                .password("1234")
                .name("금강선")
                .build();


        userRepository.save(sampleUser);


        Login login = Login.builder()
                .email("333@333")
                .password("1234")
                .build();

        // 세션 생성

        String loginJson = objectMapper.writeValueAsString(login);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken", Matchers.notNullValue()))
                .andDo(MockMvcResultHandlers.print());

//        User loggedInUser = userRepository.findById(sampleUser.getId())
//                .orElseThrow(() -> new RuntimeException());
    }

    @Test
    @DisplayName("로그인 후 권한이 필요한 페이지에 접속함 /foo")
    public void test4() throws Exception {
        // given
        User user = User.builder()
                .email("333@333")
                .password("1234")
                .name("금강선")
                .build();

//        Session session = user.addSession();
        userRepository.save(user);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/foo")
                        .contentType(MediaType.APPLICATION_JSON)
                        )
//                        .content(loginJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

//        User loggedInUser = userRepository.findById(sampleUser.getId())
//                .orElseThrow(() -> new RuntimeException());
    }

    @Test
    @DisplayName("로그인 후 컴증되지 않은 세션 값으로 권한이 필요한 페이지에 접속할 수 없음")
    public void test5() throws Exception {
        // given
        User user = User.builder()
                .email("333@333")
                .password("1234")
                .name("금강선")
                .build();

//        Session session = user.addSession();
        userRepository.save(user);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/foo")
                        .contentType(MediaType.APPLICATION_JSON)
                        )
//                        .content(loginJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("로그인 후 권한이 필요한 페이지 접속한다 /foo")
    void test6() throws Exception{

        // given
        User user = User.builder()
                .email("333@333")
                .password("1234")
                .name("금강선")
                .build();

//        Session session = user.addSession();
        userRepository.save(user);

        // expected
    }

    @Test
    @DisplayName("회원가입")
    void test7() throws Exception {

        // given
        SignUpDto signUpDto = SignUpDto.builder()
                .name("금강선")
                .password("1234")
                .email("111@111").build();
//        String loginJson = objectMapper.writeValueAsString(login);

        String json = objectMapper.writeValueAsString(signUpDto);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}