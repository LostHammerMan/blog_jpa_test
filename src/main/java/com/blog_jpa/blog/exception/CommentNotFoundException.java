package com.blog_jpa.blog.exception;

import java.util.Map;

public class CommentNotFoundException extends blogException{

    private static final String MSG = "해당 댓글이 존재하지 않습니다";

    public CommentNotFoundException() {
        super(MSG);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }


}
