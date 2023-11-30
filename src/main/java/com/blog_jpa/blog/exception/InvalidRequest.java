package com.blog_jpa.blog.exception;

import lombok.Getter;

// 잘못된 요청의 경우 발생
// 정책상 status -> 400
@Getter
public class InvalidRequest extends blogException {

    private static final String MSG = "잘못된 요청입니다";

    // 상위 exception 에서 처리
//    public String fieldName;
//    public String message;

    public InvalidRequest() {
        super(MSG);
    }

    // 상위 exception 에서 받아오는 형식으로 처리
    public InvalidRequest(String fieldName, String message) {
        super(MSG);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
