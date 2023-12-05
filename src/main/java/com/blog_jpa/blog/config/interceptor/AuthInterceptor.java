package com.blog_jpa.blog.config.interceptor;

import com.blog_jpa.blog.exception.Unauthorized;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    // 핸들러 이전 무조건 실행
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info(">> preHandle");
//        return false; // prehandle 까지만 호출됨
        String accessToken = request.getParameter("accessToken");

//        if (accessToken != null && accessToken.equals("hodolMan")){
        if (accessToken != null && !accessToken.equals("")){
            request.setAttribute("userName", accessToken);
            return true;
        }
        log.info("접근 권한 없음");
        throw new Unauthorized();
    }

    // 핸들러 실행 controller 이후 실행
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info(">> postHandle");

    }

    // view 로 보내고 난 후 실행됨
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info(">> afterCompletion");

    }
}
