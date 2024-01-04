package com.blog_jpa.blog.config.security;

import com.blog_jpa.blog.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class MethodSecurityConfig {

    private final PostRepository postRepository;
    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler(){
        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
        handler.setPermissionEvaluator(new BlogPermissionEvaluator(postRepository));
        return handler;
    }
}
