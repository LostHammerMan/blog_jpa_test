package com.blog_jpa.blog.exception;

import com.blog_jpa.blog.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

   @ResponseBody
//   @ResponseStatus(HttpStatus.NOT_FOUND) // 어노테이션의 경우 동적으로 status 를 바꿀수 없는 문제
   @org.springframework.web.bind.annotation.ExceptionHandler(blogException.class)
    public ResponseEntity<ErrorResponse> blogException(blogException e){

        // 방법 1
//        if (e instanceof InvalidRequest){
//            // 404
//        } else if (e instanceof PostNotFoundException) {
//            // 400
//        }

       int statusCode = e.getStatusCode();

       ErrorResponse responseBody = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();

       // 응답 json validation -> title : "제목에 바보를 포함할 수 없습니다"
       // 위 responsebody 의 빌더를 이용해 처리
//       if (e instanceof InvalidRequest){
//           InvalidRequest invalidRequest = (InvalidRequest) e;
//           String fieldName = invalidRequest.getFieldName();
//           String message = invalidRequest.getMessage();
//           responseBody.addValidation(fieldName, message);
//
//       }

       ResponseEntity<ErrorResponse> response = ResponseEntity.status(statusCode)
               .body(responseBody);


        return response;
   }

   @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exception(Exception e){
        log.error("예외 발생", e);

        ErrorResponse body = ErrorResponse.builder()
                .code("500")
                .message(e.getMessage())
                .build();

        ResponseEntity<ErrorResponse> response = ResponseEntity.status(500)
                .body(body);

        return response;
   }


}