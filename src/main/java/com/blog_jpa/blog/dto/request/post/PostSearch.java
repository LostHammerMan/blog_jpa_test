package com.blog_jpa.blog.dto.request.post;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder // @Builder.Default 사용하려면 클래스 단위에서 @Builder 사용
public class PostSearch {

    private static final int MAX_SIZE = 2000;

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 10; // 페이지당 출력 데이터 수

    public long getOffset(){
        return (long) (Math.max(1, page) - 1) * Math.min(size, MAX_SIZE);
    }

//    @Builder
//    public PostSearch(Integer page, Integer size) {
//        this.page = page;
//        this.size = size;
//    }
}
