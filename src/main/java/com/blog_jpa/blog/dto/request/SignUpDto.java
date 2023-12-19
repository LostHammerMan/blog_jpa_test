package com.blog_jpa.blog.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
public class SignUpDto {

    private String name;
    private String password;
    private String email;

    public SignUpDto() {
    }

    @Builder
    public SignUpDto(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }
}
