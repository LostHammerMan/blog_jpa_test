package com.blog_jpa.blog.dto.request.post;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

// PostCreate 기능 유사, 필드 유사
// 그래도 수행하는 기능이 다르다면 별도의 DTO 로 분리해야 함
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostEdit {

    @NotBlank(message = "제목입력")
    private String title;

    @NotBlank(message = "내용입력")
    private String content;



    @Builder
    public PostEdit(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
