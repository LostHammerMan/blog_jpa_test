package com.blog_jpa.blog.service;

import com.blog_jpa.blog.domain.entity.Post;
import com.blog_jpa.blog.dto.request.PostCreate;
import com.blog_jpa.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    // 단건 조회
//    public Optional<Post> getPost(Long postId){
//        return postRepository.findById(postId);
//    }
    public Post get(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        return post;
    }

    // 해당 서비스가 잘되서 Rss를 발급하는 경우
    public Post getRss(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        return post;
    }

}
