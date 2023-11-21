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

    // 엔티티에 getter 를 만드는 경우 서비스 정책을 포함시키지 말 것!!! 절대!!!!
    // ex) title 10 글자로 제한
    // 응답 전용 클래스 분리 추천(서비스 정책에 맞는) > ex) PostResponse

    /*public String getTitle(){
        return this.title.substring(0, 10);
    }*/
}
