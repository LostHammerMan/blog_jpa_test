package com.blog_jpa.blog.config.security;


import com.blog_jpa.blog.config.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserPrincipal extends User {

    private final Long userId;

    // role : 역할 -> 관리자, 사용자, 매니저
    // authority : 권한 -> 글쓰기, 글읽기, user 삭제

    public UserPrincipal(com.blog_jpa.blog.domain.entity.User findUser){
        // role 을 만들기 위해서는 ROLE_ + user 등으로 사용 / 없으면 권한
        super(findUser.getEmail(), findUser.getPassword(), List.of(
                new SimpleGrantedAuthority("ROLE_USER"),
                // ROLE prefix 를 빼는 경우 authority
                new SimpleGrantedAuthority("WRITE")));
        this.userId = findUser.getId();
    }

    public Long getUserId() {
        return userId;
    }
}
