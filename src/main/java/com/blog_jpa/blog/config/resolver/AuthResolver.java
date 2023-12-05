package com.blog_jpa.blog.config.resolver;

import com.blog_jpa.blog.config.data.UserSession;
import com.blog_jpa.blog.exception.Unauthorized;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthResolver implements HandlerMethodArgumentResolver {

    // 요청으로 넘어온 dto 를 지원하는지 확인
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    // 실제로 dto 에 값을 세팅
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        // get 방식
//        String accessToken = webRequest.getParameter("accessToken");

        // 헤더 방식
        String accessToken = webRequest.getHeader("Authorization");

        if (accessToken == null || accessToken.equals("")){
            throw new Unauthorized();
        }

        // DB 사용자 존재 여부 확인


        // 값 할당
        return new UserSession(1L);
    }
}
