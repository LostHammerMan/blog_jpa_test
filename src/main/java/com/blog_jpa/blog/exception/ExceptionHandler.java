package com.blog_jpa.blog.exception;

import com.blog_jpa.blog.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@ControllerAdvice
public class ExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        // MethodArgumentNotValidException

        if (e.hasErrors()) {
           /* FieldError fieldError = e.getFieldError();
            String errorField = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();*/
            ErrorResponse response = ErrorResponse.builder()
                    .code("400")
                    .message("잘못된 입력입니다")
                    .build();
//            response.addValidation(필드명, 에러메시지)

            for (FieldError fieldError : e.getFieldErrors()) {
                response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return response;

        } else {
            return null;
        }

    }
}