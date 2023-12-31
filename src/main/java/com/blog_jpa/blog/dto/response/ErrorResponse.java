package com.blog_jpa.blog.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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
//@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
/* > null 인 데이터, absent 데이터, Collections, Map 의 isEmpty() 가 true 인 데이터,
   Array 의 length 가 0, String 의 length 가 0 인 데이터 제외
*
* */


@Getter
//@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class ErrorResponse {

    private final String code;
    private final String message;

    private Map<String, String> validation = new HashMap<>();

    @Builder
    public ErrorResponse(String code, String message, Map<String, String> validation) {
        this.code = code;
        this.message = message;
        this.validation = validation;

    }

    public void addValidation(String fieldName, String errorMessage){
        this.validation.put(fieldName, errorMessage);
    }


}
