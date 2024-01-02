package com.blog_jpa.blog.config.annotation;

import com.blog_jpa.blog.config.security.UserPrincipal;
import com.blog_jpa.blog.domain.entity.User;
import com.blog_jpa.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

@RequiredArgsConstructor
public class BlogMockSecurityContext implements WithSecurityContextFactory<BlogMockUser> {

    private final UserRepository userRepository;

    @Override
    public SecurityContext createSecurityContext(BlogMockUser annotation) {
        User user = User.builder()
                .name(annotation.name())
                .email(annotation.email())
                .password(annotation.password())
                .build();

        userRepository.save(user);

        UserPrincipal userPrincipal = new UserPrincipal(user);
        SimpleGrantedAuthority role = new SimpleGrantedAuthority("ROLE_ADMIN");

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userPrincipal, user.getPassword()
        , List.of(role));

        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(token);

        return context;
    }
}
