package com.blog_jpa.blog.controller;

import com.blog_jpa.blog.config.security.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    public String main(){
        return "메인 페이지 입니다";
    }

    // 시큐리티 로그인 사용자 정보 가져오기
    @GetMapping("/user")
    public String user(@AuthenticationPrincipal UserPrincipal userPrincipal){
        // @AuthenticationPrincipal : UserPrincipal 객체 정보를 가져올 수 있음, 패스워드는 제외됨
        return "사용자 페이지 입니다";
    }

    @GetMapping("/admin")
    public String admin(){
        return "관리자 페이지 입니다";
    }
}
