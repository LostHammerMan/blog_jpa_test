package com.blog_jpa.blog.config.annotaion;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
//@WithSecurityContext(factory = MockUserFactory.class)
public @interface CustomWithMockUser {

    String username() default "aaa@ddd";
    String password() default "1234";
    int level() default 5;
    String mobileNumber() default "010-1111-1111";
}
