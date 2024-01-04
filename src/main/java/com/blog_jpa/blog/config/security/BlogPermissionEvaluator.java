package com.blog_jpa.blog.config.security;

import com.blog_jpa.blog.domain.entity.Post;
import com.blog_jpa.blog.exception.PostNotFoundException;
import com.blog_jpa.blog.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

@Slf4j
@RequiredArgsConstructor
public class BlogPermissionEvaluator implements PermissionEvaluator {

    private final PostRepository postRepository;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Post post = postRepository.findById((Long) targetId)
                .orElseThrow(() -> new PostNotFoundException());

        if (!post.getUserId().equals(userPrincipal.getUserId())){
            log.error("[인가실패] 해당 사용자가 작성한 글이 아닙니다 targetId = {}", targetId);
            return false;
        }

        return true;
    }
}
