package com.blog_jpa.blog.exception;

public class InvalidSigningInformation extends blogException{

    public static final String MESSAGE = "유저 정보를 확인할 수 없습니다";

    public InvalidSigningInformation() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
