package com.blog_jpa.blog.service;

import com.blog_jpa.blog.domain.entity.Post;
import com.blog_jpa.blog.dto.request.PostCreate;
import com.blog_jpa.blog.dto.request.PostSearch;
import com.blog_jpa.blog.dto.response.PostResponse;
import com.blog_jpa.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // final 로 선언된 경우
@Slf4j
public class PostService {

    private final PostRepository postRepository;

//    public Post write(PostCreate postCreate){
//    public Long write(PostCreate postCreate){
    public void write(PostCreate postCreate){

        log.info("write service called.......");
        // Dto -> Entity
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        log.info("post = {}", post.getTitle());
        log.info("post = {}", post.getContent());

        postRepository.save(post);

        // DB 저장 데이터 그대로 response
//        return postRepository.save(post); // PostCreate 엔티티가 아니므로 save() 불가, 엔티티로 변환 필요

        // primary_id 로 response
//        log.info("post.getId() = {}", post.getId());
//        return post.getId();
    }

    // post 다수 작성
    /*public Post multipleWrite(PostCreate postCreate){
        Post postList = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.saveAll(postList);
    }*/

    // 단건 조회
//    public Optional<Post> getPost(Long postId){
//        return postRepository.findById(postId);
//    }
    public PostResponse get(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        // 서비스 정책에 맞는 전용 클래스 사용하는 경우
        PostResponse postResponse = PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();

        return postResponse;
    }

    // 해당 서비스가 잘되서 Rss를 발급하는 경우
    public Post getRss(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        return post;
    }

    public List<PostResponse> getList() {
        return postRepository.findAll()
                // PostResponse 빌더가 너무 자주 사용됨 > 생성자를 통해 Post 엔티티 값을 받아오도록 변경
                /*.stream().map(post -> PostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .build())
                .collect(Collectors.toList());*/
                .stream().map(post -> new PostResponse(post))
                .collect(Collectors.toList());
    }

    // 여러 건 조회 + 페이징
//    public List<PostResponse> getListWithPaging(Pageable pageable){
//
//        // web -> page 1 -> 내부적으로 0 처리
//
//        // Pageable 객체
//        return postRepository.findAll(pageable).stream()
//                .map(post -> new PostResponse(post))
//                .collect(Collectors.toList());
//    }

    // 페이징 + queryDsl
    public List<PostResponse> getListWithPagingQueryDsl(PostSearch postSearch){
        return postRepository.getListWithQuerydsl(postSearch).stream()
                .map(post -> new PostResponse(post))
                .collect(Collectors.toList());
    }
}
