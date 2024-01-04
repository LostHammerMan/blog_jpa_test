package com.blog_jpa.blog.dto.request.comment;

import com.blog_jpa.blog.domain.entity.Comment;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
public class CommentCreate {

    @Length(min = 1, max = 8, message = "작성자는 1 ~ 8 글자로 입력")
    @NotBlank(message = "작성자를 입력해주세요")
    private String author;

    @Length(min = 6, max = 30, message = "비밀번호는 6 ~ 30 글자로 입력")
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @Length(min = 10, max = 1000, message = "내용는 10 ~ 1000 글자로 입력")
    @NotBlank(message = "내용을 입력해주세요")
    private String content;

    @Builder
    public CommentCreate(String author, String password, String content) {
        this.author = author;
        this.password = password;
        this.content = content;
    }

    // dto -> comment entity
//    public Comment toEntity(){
//        return  Comment.builder()
//                .author(this.getAuthor())
//                .password(this.getPassword())
//                .content(this.getContent())
//                .build();
//    }
}
