package com.blog_jpa.blog.exception;

// 잘못된 요청의 경우 발생
// 정책상 status -> 400
public class InvalidRequest extends blogException {

    private static final String MSG = "잘못된 요청입니다.";

    public InvalidRequest() {
        super(MSG);
    }

    public InvalidRequest(Throwable cause) {
        super(MSG, cause);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
