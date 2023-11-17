package com.blog_jpa.blog.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob // DB 에서는 long text 형식
    private String content;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // title 10 글자로 제한
    public String getTitle(){
        return this.title.substring(0, 10);
    }
}
