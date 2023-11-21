package com.blog_jpa.blog.dto.response;

import com.blog_jpa.blog.domain.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

// 서비스 정책에 맞는 클래스
// 클라 요구 사항 : json 응답에서 title 값 길이를 최대 10글자
@Getter
public class PostResponse {

    private final Long id;
    private final String title;
    private final String content;

    // 방법 2 > 이 경우 getter 메서드를 새로 구성하지 않아도 됨
    // 그러나 , 버그 발생 여지 있음
    // > 글자가 너무 짧은 경우(ex 5글자)
    @Builder
    public PostResponse(Long id, String title, String content) {
        this.id = id;
//        this.title = title.substring(0, 10); // 글자가 짧은 경우 버그 발생 여지 있음(ex 3글자)
        this.title = title.substring(0, Math.min(title.length(), 10));
        this.content = content;
    }

    // 생성자 오버로딩 > 엔티티를 통해 필드 값을 받아오도록
    public PostResponse(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }

    // title 값 길이를 최대 10글자로
    // 방법 1
    /*public String getTitle(){
        return this.title.substring(0, 10);
    }*/
}
