package com.blog_jpa.blog.controller;

import com.blog_jpa.blog.domain.entity.Post;
import com.blog_jpa.blog.repository.post.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
public class PostControllerDocsTest {

    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }


    @Test
    @DisplayName("글 단건 조회 테스트 문서 생성")
    void test1() throws Exception {


        // given
        Post post = Post.builder()
                .title("제목1")
                .content("내용1").build();

        postRepository.save(post);


        // expected
        this.mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()) // html 요청, 응답 확인
                .andDo(document("index"));
    }
}
