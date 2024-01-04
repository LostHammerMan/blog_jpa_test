package com.blog_jpa.blog.controller;

import com.blog_jpa.blog.dto.request.comment.CommentCreate;
import com.blog_jpa.blog.dto.request.comment.CommentDelete;
import com.blog_jpa.blog.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public void write(@PathVariable Long postId, @RequestBody @Valid CommentCreate request){
        commentService.write(postId, request);
    }

    // 댓글 삭제
    // @DeleteMapping 의 경우 body 가 없는 경우에만 사용, 다른 애플리케이션 서버에서 해당 body 를 무시할
     // 있기 때문에
    // body 가 있는 경우(CommonDelete), @PostMapping 사용(작동을 하더라도)
//    @DeleteMapping("/comments/{commentId}")
    @PostMapping("/comments/{commentId}/delete")
    public void delete(@PathVariable Long commentId, @RequestBody CommentDelete request){
        log.info("comments delete called.....");
        commentService.delete(commentId, request);
    }
}
