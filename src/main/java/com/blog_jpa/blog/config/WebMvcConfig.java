package com.blog_jpa.blog.config;

import com.blog_jpa.blog.config.interceptor.AuthInterceptor;
import com.blog_jpa.blog.config.resolver.AuthResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new AuthInterceptor())
//                .excludePathPatterns("/error", "/favicon.ico");
////                .excludePathPatterns("/foo"); // /foo 제외
//
//    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthResolver());
    }
}
