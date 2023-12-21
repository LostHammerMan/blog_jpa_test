package com.blog_jpa.blog;

import com.blog_jpa.blog.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

//@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class BlogJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogJpaApplication.class, args);
    }

}
