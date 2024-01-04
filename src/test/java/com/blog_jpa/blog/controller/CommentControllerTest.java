package com.blog_jpa.blog.controller;

import com.blog_jpa.blog.domain.entity.Comment;
import com.blog_jpa.blog.domain.entity.Post;
import com.blog_jpa.blog.domain.entity.User;
import com.blog_jpa.blog.dto.request.comment.CommentCreate;
import com.blog_jpa.blog.dto.request.comment.CommentDelete;
import com.blog_jpa.blog.repository.UserRepository;
import com.blog_jpa.blog.repository.comment.CommentRepository;
import com.blog_jpa.blog.repository.post.PostRepository;
import com.blog_jpa.blog.service.CommentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc // SpringBootTest 환경에서 Mock 사용하는 경우 필수
@SpringBootTest
@Slf4j
public class CommentControllerTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void clean(){
        commentRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("댓글 작성")
    public void test1() throws Exception {

        User user = User.builder()
                .name("이병탁")
                .email("qwe@qwe")
                .password("1234").build();

        userRepository.save(user);

        Post post = Post.builder()
                .user(user)
                .title("제목111")
                .content("내용111")
                .build();

        postRepository.save(post);

        CommentCreate request = CommentCreate.builder()
                .author(post.getUser().getName())
                .password("123456")
                .content("댓글1111111111111111")
                .build();

        String json = objectMapper.writeValueAsString(request);
//        commentService.write(post.getId(), request);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/posts/{postId}/comments", post.getId())
                .content(json)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("댓글1111111111111111"))
                .andDo(MockMvcResultHandlers.print());

        Comment findComment = commentRepository.findAll().get(0);

        Assertions.assertEquals("이병탁", findComment.getAuthor());
        Assertions.assertEquals("댓글1111111111111111", findComment.getContent());
        Assertions.assertNotEquals("123456", findComment.getPassword());
        Assertions.assertTrue(passwordEncoder.matches("123456", findComment.getPassword()));
    }

    @Test
    @DisplayName("댓글 삭제")
    public void test2() throws Exception {
        User user = User.builder()
                .name("이병탁")
                .email("qwe@qwe")
                .password("1234").build();

        userRepository.save(user);

        Post post = Post.builder()
                .user(user)
                .title("제목111")
                .content("내용111")
                .build();

        postRepository.save(post);

        CommentCreate request = CommentCreate.builder()
                .author(post.getUser().getName())
                .password("123456")
                .content("댓글1111111111111111")
                .build();

        CommentCreate request2 = CommentCreate.builder()
                .author(post.getUser().getName())
                .password("123456")
                .content("댓글22222222222222222")
                .build();

        String encryptedPwd = passwordEncoder.encode("123456");
        CommentDelete commentDelete = new CommentDelete();
        commentDelete.setPassword(encryptedPwd);

        String json = objectMapper.writeValueAsString(commentDelete);

        commentService.write(post.getId(), request);
        commentService.write(post.getId(), request2);

        mockMvc.perform(MockMvcRequestBuilders.post("/comments/{commentId}/delete", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}
