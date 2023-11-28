package com.blog_jpa.blog.exception;

public abstract class blogException extends RuntimeException {
    public blogException(String message) {
        super(message);
    }

    public blogException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();
}
