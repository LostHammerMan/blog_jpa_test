package com.blog_jpa.blog.exception;

public class UserNotFoundException extends blogException {

    private static final String MSG = "존재하지 않는 유저입니다";

    public UserNotFoundException(){
        super(MSG);
    }
    @Override
    public int getStatusCode() {
        return 404;
    }
}
