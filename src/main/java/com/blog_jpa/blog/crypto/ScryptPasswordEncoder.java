package com.blog_jpa.blog.crypto;

import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;

// Scrypt 방식의 패스워드 인코더
//@Profile("default")
//@Component
//public class ScryptPasswordEncoder implements PasswordEncoder {
//
////    private static final SCryptPasswordEncoder encoder = new SCryptPasswordEncoder(
////            16,
////            8,
////            1,
////            32,
////            64
////    );
//
////    public String encrypt(String rawPassword){
////        return encoder.encode(rawPassword);
////    }
//
////    public boolean matches(String rawPassword, String encryptedPassword){
////        return encoder.matches(rawPassword, encryptedPassword);
////    }
//    ret
//}
