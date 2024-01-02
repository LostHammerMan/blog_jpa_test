package com.blog_jpa.blog.config.annotaion;

import com.blog_jpa.blog.repository.UserRepository;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.lang.annotation.Annotation;

//public class MockUserFactory implements WithSecurityContextFactory {
//
//    private UserRepository userRepository;
//
//    @Override
//    public SecurityContext createSecurityContext(CustomWithMockUser annotation) {
//        int level = annotation.level();
//        String username = annotation.username();
//        String password = annotation.password();
//        String mobileNum = annotation.mobileNumber();
//
//        userRepository.save(
//
//        )
//
//        return null;
//    }
//}
