package com.blog_jpa.blog.exception;

// 정책상 status -> 404
public class PostNotFoundException extends blogException {

    private static final String MESSAGE = "존재하지 않는 글입니다";


    public PostNotFoundException() {
        super(MESSAGE);
    }

    public PostNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
