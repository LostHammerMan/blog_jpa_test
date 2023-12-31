package com.blog_jpa.blog.controller;

import com.blog_jpa.blog.config.annotation.BlogMockUser;
import com.blog_jpa.blog.domain.entity.Post;
import com.blog_jpa.blog.domain.entity.User;
import com.blog_jpa.blog.dto.request.post.PostCreate;
import com.blog_jpa.blog.dto.request.post.PostEdit;
import com.blog_jpa.blog.repository.post.PostRepository;
import com.blog_jpa.blog.repository.UserRepository;
import com.blog_jpa.blog.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
    private UserRepository userRepository;

    @Autowired
    private PostService postService;

    // 각각의 메서드가 실행되기 전 우선적으로 실행됨
    @AfterEach // @BeforeEach  사용시 시큐리티 컨텍스트가 먼저 작동해 컨텍스트에서 저장된 유저 삭제함
    void clean(){
        userRepository.deleteAll();
        postRepository.deleteAll();
    }


    @Test
    @DisplayName("글 작성 요청시 hello world 출력")
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
//    @WithMockUser(username = "aaa@ddd",
//            roles = {"ADMIN"},
//            password = "1234") // 해당 속성대로 인증이 되었다고 가정
    @BlogMockUser
    @DisplayName("글작성 + 시큐리티 테스트")
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
//        mockMvc.perform(MockMvcRequestBuilders.post("/posts?authorization=hodolMan")
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
//                        .header("authorization", "hodolMan")
                        .header("authorization", "")
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

//            postService.write(postList);
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
    @DisplayName("페이지를 0 으로 요청하면 첫 페이지를 가져온다.")
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
        mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=0&size=10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("제목29"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("내용29"))
                .andDo(MockMvcResultHandlers.print());

    }

    // post 여러 개 + 페이징
    @Test
//    @WithMockUser(username = "aaa@ddd",
//            roles = {"ADMIN"},
//            password = "1234") // 해당 속성대로 인증이 되었다고 가정
    @BlogMockUser
    @DisplayName("post 수정 test")
    public void test7() throws Exception{

        // given
        User findUser = userRepository.findAll().get(0);

        Post post = Post.builder()
                .title("제목1")
                .content("내용1")
                .user(findUser)
                .build();

        postRepository.save(post);
        log.info("post.getId={}", post.getId());

        PostEdit postEdit = PostEdit.builder()
                .title("변경된 제목")
                .content("변경된 내용")
                .build();


        // expected
        mockMvc.perform(MockMvcRequestBuilders.patch("/posts/{postId}", post.getId())
                        .header("authorization", "hodolMan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit)))
                .andExpect(MockMvcResultMatchers.status().isOk())

//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("변경된 제목"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("변경된 내용"))
                .andDo(MockMvcResultHandlers.print());

        log.info("postEdit = {}", postEdit);

    }

    @Test
//    @WithMockUser(username = "aaa@ddd",
//            roles = {"ADMIN"},
//            password = "1234") // 해당 속성대로 인증이 되었다고 가정
    @BlogMockUser()
    @DisplayName("post 삭제 테스트")
    public void postDelete() throws Exception {

        // given
        User findUser = userRepository.findAll().get(0);



        List<Post> postList = IntStream.range(1, 30)
                .mapToObj(i -> {
                    return Post.builder()
                            .title("title" + i)
                            .content("content" + i)
                            .user(findUser)
                            .build();
                }).toList();

        postRepository.saveAll(postList);

        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/{postId}", postList.get(0).getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("존재하지 않는 게시글 조회")
    public void test8() throws Exception{

        // given
        Post post = Post.builder()
                .title("제목1")
                .content("내용1")
                .build();

        postRepository.save(post);

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}", 3L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    //
    @Test
    @DisplayName("post 수정 - 존재하지 않는 글")
    public void test9() throws Exception{

        // given
        Post post = Post.builder()
                .title("제목1")
                .content("내용1")
                .build();

        postRepository.save(post);
        log.info("post.getId = {}", post.getId());

        // when
        PostEdit postEdit = PostEdit.builder()
                .title("변경된 제목")
                .content("변경된 내용")
                .build();

        log.info("postEdit = {}", postEdit);

        // then
        mockMvc.perform(MockMvcRequestBuilders.patch("/posts/{postId}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("post 작성 - 제목에 바보는 포함될 수 없음")
    public void test10() throws Exception {

        Post post = Post.builder()
                .title("나는 바보입니다")
                .content("내용1")
                .build();

        postRepository.save(post);

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(post)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    // API 문서 생성

    // 클라이언트 입장에서는 어떤 API 있는지 모름
    // > 만든 API 를 문서화해서 전달할 필요 있음

    // spring RestDocs 를 이용해 문서화
    // 장 > 운영코드에 영향 없음
    //    > Test 케이스 실행 -> 통과시 문서 생성


}