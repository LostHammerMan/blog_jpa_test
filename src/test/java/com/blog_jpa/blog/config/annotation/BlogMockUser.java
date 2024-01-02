package com.blog_jpa.blog.config.annotation;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = BlogMockSecurityContext.class)
public @interface BlogMockUser {

    String email() default "aaa@aaa";
    String password() default "1234";
    String name() default "전재학";
//    String role() default "ROLE_ADMIN";
}
