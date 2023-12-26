package com.blog_jpa.blog.config.resolver;

import com.blog_jpa.blog.config.AppConfig;
import com.blog_jpa.blog.config.data.UserSession;
import com.blog_jpa.blog.exception.Unauthorized;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.session.SessionRepository;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

// 파라미터 타입으로 UserSession 이 있는 경우 인증을 거치도록 함
@RequiredArgsConstructor
@Slf4j
public class AuthResolver implements HandlerMethodArgumentResolver {

//    private static final String KEY = "ur69aMjgadSPyXX1BXJ/eRVCdkvi9ah7cdxHHB35kxI=";
    private final SessionRepository sessionRepository;
    private final AppConfig appConfig;


    // 요청으로 넘어온 dto 를 지원하는지 확인
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    // JWT 사용
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        log.info(">>>>>>>>>>>{}", appConfig.toString());

//        log.info("name = {}", appConfig.getHello().get("name"));
//        log.info("home = {}", appConfig.getHello().get("home"));
//        log.info("hobby = {}", appConfig.getHello().get("hobby"));

        //

        String jws = webRequest.getHeader("Authorization");

        if (jws == null || jws.equals("")){
            throw new Unauthorized();
        }

        // String -> byte
//        byte[] decodedKey = Base64.decodeBase64(appConfig.jwtKey);

        // jws 토큰 복호화
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(appConfig.getJwtKey())
                    .build()
                    .parseClaimsJws(jws);
            log.info("claims = {}", claims);

            String userId = claims.getBody().getSubject();
            return new UserSession(Long.parseLong(userId));

        }catch (JwtException e){
            throw new Unauthorized();
        }

        // DB 사용자 존재 여부 확인 > JWT 방식을 사용하는 경우 필요 없음
//        Session session = sessionRepository.findByAccessToken(accessToken)
//                .orElseThrow(() -> new Unauthorized());

    }

    // 실제로 dto 에 값을 세팅
//    @Override
//    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
//
//        // get 방식
////        String accessToken = webRequest.getParameter("accessToken");
//
//        // 헤더 방식
//        String accessToken = webRequest.getHeader("Authorization");
//
//        if (accessToken == null || accessToken.equals("")){
//            throw new Unauthorized();
//        }
//
//        // DB 사용자 존재 여부 확인
//        Session session = sessionRepository.findByAccessToken(accessToken)
//                .orElseThrow(() -> new Unauthorized());
//
//        // 값 할당
//        return new UserSession(session.getUser().getId());
//    }

    // 헤더에서 쿠키를 받아오는 방식
//    @Override
//    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
//
//        // get 방식
////        String accessToken = webRequest.getParameter("accessToken");
//
//        // 헤더 방식
//        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
//
//        if (servletRequest == null){
//            log.error("servletRequest null");
//            throw new Unauthorized();
//        }
//
//        Cookie[] cookies = servletRequest.getCookies();
//
//        if (cookies.length == 0){
//            log.error("쿠키가 없음");
//            throw new Unauthorized();
//        }
//
//        String accessToken = cookies[0].getValue();
//
//        // DB 사용자 존재 여부 확인
//        Session session = sessionRepository.findByAccessToken(accessToken)
//                .orElseThrow(() -> new Unauthorized());
//
//        // 값 할당
//        return new UserSession(session.getUser().getId());
//    }
}
