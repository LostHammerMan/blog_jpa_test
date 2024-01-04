package com.blog_jpa.blog.service;

import com.blog_jpa.blog.domain.entity.Comment;
import com.blog_jpa.blog.domain.entity.Post;
import com.blog_jpa.blog.dto.request.comment.CommentCreate;
import com.blog_jpa.blog.dto.request.comment.CommentDelete;
import com.blog_jpa.blog.exception.CommentNotFoundException;
import com.blog_jpa.blog.exception.InvalidPasswordException;
import com.blog_jpa.blog.exception.InvalidSigningInformation;
import com.blog_jpa.blog.exception.PostNotFoundException;
import com.blog_jpa.blog.repository.comment.CommentRepository;
import com.blog_jpa.blog.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void write(Long postId, CommentCreate request) {

        Post findPost = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException()
        );

        String encryptedPwd = passwordEncoder.encode(request.getPassword());

        Comment comment = Comment.builder()
                .author(request.getAuthor())
                .password(encryptedPwd)
                .content(request.getContent())
                .build();

        findPost.addCommend(comment);
    }

    public void delete(Long commentId, CommentDelete request) {

        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException());

        String rawPwd = request.getPassword();
        String encryptedPwd = findComment.getPassword();

        if (!passwordEncoder.matches(rawPwd, encryptedPwd)){
            log.error("댓글 비밀번호가 일치하지 않습니다.");
            throw new InvalidPasswordException();
        }

        commentRepository.deleteById(commentId);
    }
}
