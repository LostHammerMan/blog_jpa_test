package com.blog_jpa.blog.exception;

public class AlreadyExistUserException extends blogException{

    public static final String MESSAGE = "이미 존재하는 계정입니다";

    public AlreadyExistUserException() {
        super(MESSAGE);
    }

    public AlreadyExistUserException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
