package com.blog_jpa.blog.domain.entity;

import lombok.Builder;
import lombok.Getter;

// 엔티티와 같은 영역에 추가함
@Getter
public class PostEditor {

    // 수정할 수 있는 필드에 대해서만
    private final String title;
    private final String content;

    // : 생성자로 체크하는 방법
    @Builder
    public PostEditor(String title, String content) {
//        this.title = title != null ? title : this.getTitle();
        this.title = title;
//        this.content = content != null ? content : this.getContent();
        this.content = content;
    }
}
