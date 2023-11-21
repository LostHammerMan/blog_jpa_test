package com.blog_jpa.blog.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostSearch {

    private int page;
    private int size; // 페이지당 출력 데이터 수

    @Builder
    public PostSearch(int page, int size) {
        this.page = page;
        this.size = size;
    }
}
