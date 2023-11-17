package com.blog_jpa.blog.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/*
* {
*       "code" : "400",
*       "message" : "잘못된 요청"
*       "validation" : {
*                           "title" : "값을 입력해주세요"
*                       }
* }
*
*
*
* */
//@RequiredArgsConstructor
@Getter
public class ErrorResponse {

    private final String code;
    private final String message;

    private final Map<String, String> validation = new HashMap<>();

    @Builder
    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void addValidation(String fieldName, String errorMessage){
        this.validation.put(fieldName, errorMessage);
    }


}
