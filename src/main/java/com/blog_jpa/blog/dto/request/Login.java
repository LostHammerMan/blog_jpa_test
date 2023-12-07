package com.blog_jpa.blog.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Login {

    @NotBlank(message = "아이디 입력해주세요")
    private String email;

    @NotBlank(message = "비밀번호 입력해주세요")
    private String password;

    public Login() {
    }

    @Builder
    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
