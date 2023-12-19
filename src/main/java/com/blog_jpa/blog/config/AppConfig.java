package com.blog_jpa.blog.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "hodolman")
public class AppConfig {

    private byte[] jwtKey;

    public void setJwtKey(String jwtKey) {
        this.jwtKey = Base64.getDecoder().decode(jwtKey);
    }

    public byte[] getJwtKey() {
        return jwtKey;
    }

    //    public List<String> hello;

//    public Hello hello;

//    @Data
//    public static class Hello {
//
//        public String name;
//        public String home;
//        public String hobby;
//        public Long age;
//    }
}
