package com.blog_jpa.blog.service;

import com.blog_jpa.blog.domain.entity.Post;
import com.blog_jpa.blog.dto.request.PostCreate;
import com.blog_jpa.blog.repository.PostRepository;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    public void dbClean(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1(){


        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목123")
                .content("내용123")
                .build();

        // when
        postService.write(postCreate);


        // then
        Assertions.assertEquals(1L, postRepository.count());

        Post findPost = postRepository.findAll().get(0);
        Assertions.assertEquals("제목123", findPost.getTitle());
        Assertions.assertEquals("내용123", findPost.getContent());
    }

    // 단건 조회
    @Test
    @DisplayName("Post 단건 조회")
    public void test2(){

        // given
        Post post = Post.builder()
                .title("제목12111111111111111")
                .content("내용1")
                .build();

        postRepository.save(post);

        Long postId = 3L;

        // when
        Post findPost = postService.get(post.getId());
        log.info("findPost.title = {}", findPost.getTitle());
        log.info("findPost.content = {}", findPost.getContent());

        // then
        Assertions.assertNotNull(findPost);
//        Assertions.assertEquals(5L, postRepository.count());
//        Assertions.assertEquals("제목2", findPost.getTitle());
//        Assertions.assertEquals("내용2", findPost.getContent());
    }

    // 클라이언트 요구 사항 :
    //  - json 응답에서 title 값 길이를 최대 10글자로 제한해주세요

}