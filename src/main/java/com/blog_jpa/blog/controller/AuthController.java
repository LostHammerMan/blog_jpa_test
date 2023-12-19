package com.blog_jpa.blog.controller;

import com.blog_jpa.blog.config.AppConfig;
import com.blog_jpa.blog.dto.request.Login;
import com.blog_jpa.blog.dto.request.SignUpDto;
import com.blog_jpa.blog.dto.response.SessionResponse;
import com.blog_jpa.blog.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.security.auth.message.config.AuthConfig;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

// id, pwd 를 받아 토큰 발급
@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

//    private final UserRepository userRepository;
        private final AuthService authService;
        private final AppConfig appConfig;

//        private final String KEY = "ur69aMjgadSPyXX1BXJ/eRVCdkvi9ah7cdxHHB35kxI=";



    // DB 통한 검증
//    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login){

        // json 아이디, 비번
//        log.info("\t login = {}", login);
//        String accessToken = authService.signIn(login);
//        return new SessionResponse(accessToken);
        return null;
        // 토큰 응답
    }

    // JWT
    @PostMapping("/auth/login")
    public SessionResponse login2(@RequestBody Login login/*, HttpServletResponse response*/){

        // json 아이디, 비번
        log.info("\t login = {}", login);
//        String accessToken = authService.signIn(login);
        Long userId = authService.signIn(login);

        // JWT 적용
        // 키 생성(자바)
//        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        Base64.getEncoder().encode(key.getEncoded());

//        log.info("key.getEncoded() = {}", key.getEncoded() );
//        byte[] encodedKey = key.getEncoded();

        // 암호화 할떄 사용하는 키

        // cf) Byte64 : 바이트를 스트링으로 혹은 그 반대로 컨버팅
//        String strKey = Base64.getEncoder().encodeToString(encodedKey);

//        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(appConfig.getJwtKey()));
        SecretKey key = Keys.hmacShaKeyFor(appConfig.getJwtKey());

        String jws = Jwts.builder()
                .setSubject(String.valueOf(userId))
                .signWith(key)
                .setIssuedAt(new Date())
                .compact();

        return new SessionResponse(jws);



        // >> 쿠키 생성
        // 1. HttpResponse
//        response.addCookie();
        // 2. ResponseCookie (스프링 5 부터 사용)
        // > Set-Cookie response header 에 원하는 속성 추가 가능
//        ResponseCookie cookie = ResponseCookie.from("SESSION", accessToken)
//                .domain("localhost") // 운영 서버스에 맞게 설정 // todo 서버 환영에 따른 분리 필요
//                .path("/") // Cookie 헤더를 전송하기 위해 요청되는 URL 내 반드시 존재해야 하는 URL 경로
//                .httpOnly(true) // Cross-site 스크립팅 공격을 방지하기 위한 옵션
//                .secure(false) // Https 프로토콜 상에서 암호화된 요청을 위한 옵션
//                .maxAge(Duration.ofDays(30))
//                .sameSite("Strict") // 서로 다른 도메인간의 쿠키 전송에 대한 보안을 설정
//                .build();
//
//        log.info(">>>>>> cookie = {}", cookie);
//
//
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.SET_COOKIE, cookie.toString())
//                .build();

    }

    // 회원가입
    @PostMapping("/auth/signUp")
    public void signUp(@RequestBody SignUpDto signUpDto){
        authService.signUp(signUpDto);

    }
}
