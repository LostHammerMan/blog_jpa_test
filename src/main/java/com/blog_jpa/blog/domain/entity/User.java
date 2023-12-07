package com.blog_jpa.blog.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter
@ToString
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private LocalDateTime createAt = LocalDateTime.now();

    // 유저 한명 -- 세션 여러 개 (일대다)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Session> sessions = new ArrayList<>();

    // 세션 발급
    public Session addSession(){
        Session session = Session.builder()
                .user(this)
                .build();

        sessions.add(session);
        return session;
    }

    @Builder
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.createAt = LocalDateTime.now();
    }
}
