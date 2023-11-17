package com.blog_jpa.blog.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

/*@ToString
@Getter @Setter*/
@Data
@NoArgsConstructor
public class PostCreate {

    @NotBlank(message = "제목 입력해주세요")
    public String title;

    @NotBlank(message = "내용 입력해주세요")
    public String content;

    // 매개변수 위치를 변경하는 경우 버그 발생
    // -> @Builder 사용,
    @Builder // 생성자에 사용하는 것 추천
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Builder 패턴을 만들어 값을 세팅하는 경우 값이 fix 됨
    // 값을 변경 하고자 하는 경우, 메서드를 통해 새로운 객체를 만들어 값 변경
    public PostCreate changeTitle(String title){
        return PostCreate.builder()
                .title(title)
                .content(content)
                .build();
    }

    // 빌더의 장점 - 면접 대비
    /* 1 가독성(값 생성에 대한 유연함)
    *  2 필요한 값만 받을 수 있음 // -> 생성자 오버로딩 가능조건 찾아보기
    *  3 객체의 불변성(매개변수 위치 변경 관련)
    *
    * */
}
