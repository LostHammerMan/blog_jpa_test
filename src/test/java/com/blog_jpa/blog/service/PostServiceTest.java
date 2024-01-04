package com.blog_jpa.blog.service;

import com.blog_jpa.blog.domain.entity.Post;
import com.blog_jpa.blog.domain.entity.User;
import com.blog_jpa.blog.dto.request.post.PostCreate;
import com.blog_jpa.blog.dto.request.post.PostEdit;
import com.blog_jpa.blog.dto.request.post.PostSearch;
import com.blog_jpa.blog.dto.response.PostResponse;
import com.blog_jpa.blog.exception.PostNotFoundException;
import com.blog_jpa.blog.repository.post.PostRepository;
import com.blog_jpa.blog.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void dbClean(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1(){

        // given
        User user = User.builder()
                .name("전재학")
                .email("qwe@qwe")
                .password("1234")
                .build();

        userRepository.save(user);

        PostCreate postCreate = PostCreate.builder()
                .title("제목123")
                .content("내용123")
                .build();

        // when
        postService.write(user.getId(), postCreate);


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
        PostResponse response = postService.get(post.getId());
        log.info("findPost.title = {}", response.getTitle());
        log.info("findPost.content = {}", response.getContent());

        // then
        Assertions.assertNotNull(response);
//        Assertions.assertEquals(5L, postRepository.count());
//        Assertions.assertEquals("제목2", findPost.getTitle());
//        Assertions.assertEquals("내용2", findPost.getContent());
    }



    // 클라이언트 요구 사항 :
    //  - json 응답에서 title 값 길이를 최대 10글자로 제한해주세요
    // > 이런 처리는 클라에서 하는게 좋다

    // post 여러 개 조회
    @Test
    @DisplayName("post 여러 개 조회")
    public void test3(){

        // given
        for (int i=0; i<100; i++){
            Post postList = Post.builder()
                    .title("제목" + i)
                    .content("내용" + i)
                    .build();

            postRepository.saveAll(List.of(postList)); // 여러 개 데이터를 save 하는 경우
        }
        // when
        List<PostResponse> list = postService.getList();

        // then
        Assertions.assertEquals(100, list.size());
    }

    // post 1 페이지 조회
//    @Test
//    @DisplayName("post 1페이지 조회")
//    public void test4(){
//
//        // given
//        List<Post> requestPost = IntStream.range(1, 30) // = for (int i=1; i<30; i++){...}
//                .mapToObj(i -> {
//                    return Post.builder()
//                            .title("post 제목" + i)
//                            .content("post 내용" + i)
//                            .build();
//        })
//                .collect(Collectors.toList());
//        postRepository.saveAll(requestPost);
//
//        // sql
//        // select, limit, offset
//
//
//        // when
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
////        List<PostResponse> posts = postService.getListWithPaging(pageable);
//
//        // then
//        Assertions.assertEquals(10, posts.size());
//        Assertions.assertEquals("post 제목29", posts.get(0).getTitle());
//        Assertions.assertEquals("post 내용29", posts.get(0).getContent());
//    }

    // 페이징 + querydsl
    @Test
    @DisplayName("페이징 + querydsl")
    public void test5(){

        // given
        List<Post> requestPost = IntStream.range(0, 30) // = for (int i=1; i<30; i++){...}
                .mapToObj(i -> {
                    return Post.builder()
                            .title("post 제목" + i)
                            .content("post 내용" + i)
                            .build();
                })
                .collect(Collectors.toList());
        postRepository.saveAll(requestPost);

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .size(10)
                .build();


        // when
        List<PostResponse> posts = postService.getListWithPagingQueryDsl(postSearch);

        // then
        Assertions.assertEquals(10, posts.size());
        Assertions.assertEquals("post 제목29", posts.get(0).getTitle());
//        Assertions.assertEquals("post 내용29", posts.get(0).getContent());
    }

    @Test
    @DisplayName("post 수정 - 존재하지 않는 글")
    public void test6(){

        Post post = Post.builder()
                .title("제목1")
                .content("내용1")
                .build();

        log.info("post = {}", post);

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("변경된 제목")
                .content("변경된 내용")
                .build();
        // when
        postService.editPost(post.getId(), postEdit);

//        log.info("modifyPost = {}", modifyPost);

        // then
        Post changePost = postRepository.findById(post.getId()).orElseThrow(() ->
            new RuntimeException("글이 존재하지 않음")
        );

        log.info("changePost = {}", changePost);
        Assertions.assertEquals("변경된 제목", changePost.getTitle());
        Assertions.assertEquals("변경된 내용", changePost.getContent());
    }

    @Test
    @DisplayName("post 수정")
    public void test7(){

        Post post = Post.builder()
                .title("제목1")
                .content("내용1")
                .build();

        log.info("post = {}", post);

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("변경된 제목")
//                .title(null)
                .content("변경된 내용")
                .build();
        // when
        postService.editPost(post.getId(), postEdit);

//        log.info("modifyPost = {}", modifyPost);

        // then
        Post changePost = postRepository.findById(post.getId()).orElseThrow(() ->
                new RuntimeException("글이 존재하지 않음")
        );


        Assertions.assertEquals("변경된 제목", changePost.getTitle());
        Assertions.assertEquals("변경된 내용", changePost.getContent());

    }

    // 삭제
    @Test
    @DisplayName("post 삭제 - 존재하지 않는 글")
    public void test8(){

        List<Post> postList = IntStream.range(1, 30)
                .mapToObj(i -> {
                    return Post.builder()
                            .title("title" + i)
                            .content("content" + i)
                            .build();
                }).toList();

        postRepository.saveAll(postList);
        List<Post> beforePostList = postRepository.findAll();
        Assertions.assertEquals(29, postRepository.count());

        log.info("before postList = {}", beforePostList.get(0));
        log.info("===============================");

        postService.delete(100L);
        List<Post> afterPostList = postRepository.findAll();
        log.info("after postLIst = {}", afterPostList.get(0));

        Assertions.assertEquals(28, postRepository.count());
    }

    @Test
    @DisplayName("글 1개 조회 + 예외처리")
    public void test2_1(){

        // given
        Post post = Post.builder()
                .title("제목12111111111111111")
                .content("내용1")
                .build();

        postRepository.save(post);

        // expected // 예외 검증
        PostNotFoundException e = Assertions.assertThrows(PostNotFoundException.class, () -> {
            postService.get(post.getId() + 1L);
        }
        );

        Assertions.assertEquals("존재하지 않는 글입니다", e.getMessage());
    }
}