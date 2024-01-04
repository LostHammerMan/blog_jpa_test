package com.blog_jpa.blog.exception;

public class InvalidPasswordException extends blogException{

    private static final String MSG = "댓글 비밀번호가 일치하지 않습니다";

    public InvalidPasswordException() {
        super(MSG);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
