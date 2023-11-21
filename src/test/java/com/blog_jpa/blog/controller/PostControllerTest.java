package com.blog_jpa.blog.controller;

import com.blog_jpa.blog.domain.entity.Post;
import com.blog_jpa.blog.dto.request.PostCreate;
import com.blog_jpa.blog.repository.PostRepository;
import com.blog_jpa.blog.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//@SpringBootTest -> 스프링이 관리하는 모든 빈을 등록시켜서 통합 테스트 진행 > 무거움
//@WebMvcTest
@AutoConfigureMockMvc // SpringBootTest 환경에서 Mock 사용하는 경우 필수
@SpringBootTest
@Slf4j
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    // 각각의 메서드가 실행되기 전 우선적으로 실행됨
    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }


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

                        // 아래와 같이 쓰는건 불편 위처럼 생성자로 생성
                        .content("{\"title\" : \"제목1\", \"content\" : \"내용1\"}")
                )
//                        .param("title", "글 제목")
//                        .param("content", "글 내용"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{}"))

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

        // before
        /// 테스트 수행시 각각의 test 메서드가 서로 영향을 주지 않도록 짜는 것이 좋다
        //postRepository.deleteAll(); // 비추

        // given
            // PostCreate 생성자 매개변수의 순서를 바꾸는 경우 문제 발생
//        PostCreate request = new PostCreate("제목입니다33", "내용입니다33");
        PostCreate request = PostCreate.builder()
                .title("제목입니다44")
                .content("내용입니다44")
                .build();

//        PostCreate request2 = request.changeTitle("제목임55");

        // json 으로 변환해주는 Jackson library 사용(필수 암기)
        // ObjectMapper 를 빈으로 주입받아도 사용 가능
//        ObjectMapper objectMapper = new ObjectMapper();

        // 자바 객체 -> json
        String json = objectMapper.writeValueAsString(request);
        log.info("json = {}", json);


        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/post2")
                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"title\" : \"제목입니다\", \"content\" : \"내용입니다\"}"))
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        // then
//        Assertions.assertEquals(1L, postRepository.count());

        // DB 잘 저장되었는지 확인
        /*Post postExam = postRepository.findAll().get(0);
        log.info("postExam.getTitle = {}", postExam.getTitle());
        log.info("postExam.getContent = {}", postExam.getContent());

        Assertions.assertEquals("제목입니다33", postExam.getTitle());
        Assertions.assertEquals("내용입니다33", postExam.getContent());*/

    }

    // 단건 조회
    @Test
    @DisplayName("단건 조회 테스트")
    public void test4() throws Exception {

        // given
        Post post = Post.builder()
                .title("asd123456789011121231313")
                .content("내용1")
                .build();

        postRepository.save(post);

        log.info("post.getTitle() = {}", post.getTitle());
        log.info("post.getContent() = {}", post.getContent());

        // 클라이언트 요구사항
        // json 응답에서 title 값 길이를 최대 10글자
        // post entity <-> PostResponse class

        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}", post.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(post.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("asd1234567"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("내용1"))
                .andDo(MockMvcResultHandlers.print());
    }

    // 글 여러 개 조회
    @Test
    @DisplayName("post 여러 개 조회")
    public void test5() throws Exception{
        // given
        for (int i=0; i<10; i++){
            PostCreate postList = PostCreate.builder()
                    .title("제목" + i)
                    .content("내용" + i)
                    .build();

            postService.write(postList);
        }

        //
        mockMvc.perform(MockMvcRequestBuilders.get("/posts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("제목0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("내용0"))
                .andDo(MockMvcResultHandlers.print());
    }

    // post 여러 개 + 페이징
    @Test
    @DisplayName("post 여러 개 + 페이징")
    public void test6() throws Exception{

        // given
        List<Post> postList = IntStream.range(1, 30)
                .mapToObj( i->{
                        return Post.builder()
                                .title("제목" + i)
                                .content("내용" + i)
                                .build();
                        }
                ).collect(Collectors.toList());

        postRepository.saveAll(postList);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=1&sort=id,desc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("제목29"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("내용29"))
                .andDo(MockMvcResultHandlers.print());

    }
}