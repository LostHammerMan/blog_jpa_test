package com.blog_jpa.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.ModelResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest -> 스프링이 관리하는 모든 빈을 등록시켜서 통합 테스트 진행 > 무거움
@WebMvcTest
@Slf4j
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("/posts 요청시 hello world 출력")
    void test() throws Exception {

        // 글 제목

        // 글 내용
        // 사용자 데이터 : id, name, level -> 내용 모두 표시하기엔 한계

        // json 데이터의 경우, 아래와 같이 데이터 온전하게 표현 가능, 클래스로 표현하기에도 간편
        /* {
            "title" : "xxx",
            "content" : "xx"
            "user" : {
                        "id" : "xxx"
                        "name" : "xx"
                      }

               }
        * */

        //expected
        // content-type : 서버로 요청할 때나 혹은 요청을 받을 때 리소스의 media type 나타냄
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\" : \"제목1\", \"content\" : \"내용1\"}")
                )
//                        .param("title", "글 제목")
//                        .param("content", "글 내용"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("hello world"))

                // http 요청에 대한 summary 출력
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("/posts 요청시 title 값은 필수")
    public void test2() throws Exception {
        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"title\" : \"\", \"content\" : \"\"}"))
//                .content("{\"title\" : null, \"content\" : \"내용2222\"}"))
                        .content("{\"title\" : null, \"content\" : null}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
//                .andExpect(MockMvcResultMatchers.content().string("hello world"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("no title!!!!")) // json 검증시
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400")) // json 검증시
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("잘못된 요청입니다")) // json 검증시
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.title").value("제목 입력해주세요")) // json 검증시
                .andDo(MockMvcResultHandlers.print());


    }

    @Test
    @DisplayName("write test")
    public void test3() throws Exception {
        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"title\" : \"\", \"content\" : \"\"}"))
//                .content("{\"title\" : null, \"content\" : \"내용2222\"}"))
                        .content("{\"title\" : null, \"content\" : null}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
//                .andExpect(MockMvcResultMatchers.content().string("hello world"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("no title!!!!")) // json 검증시
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400")) // json 검증시
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("잘못된 요청입니다")) // json 검증시
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.title").value("제목 입력해주세요")) // json 검증시
                .andDo(MockMvcResultHandlers.print());
    }
}