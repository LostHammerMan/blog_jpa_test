package com.blog_jpa.blog.domain.entity;

import com.blog_jpa.blog.dto.request.comment.CommentCreate;
import com.blog_jpa.blog.dto.request.post.PostEdit;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Lob;
import lombok.*;

import jakarta.persistence.*;

import java.util.List;

@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter // @Setter -> 엔티티에 직접적인 setter는 지양
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob // DB 에서는 long text 형식
    private String content;

    @ManyToOne
    @JoinColumn
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @Builder
    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void edit(PostEdit postEdit){

        this.title = postEdit.getTitle() != null ? postEdit.getTitle() : this.getTitle();
        this.content = postEdit.getContent() != null ? postEdit.getContent() : this.getContent();
//        this.title = title;
//        this.content = content;
    }

    public Long getUserId(){
        return this.user.getId();
    }

    public void addCommend(Comment comment) {
        comment.setPost(this);
        this.comments.add(comment);
    }

    // 수정 메서드 1
   /* public void modifyPost(PostEdit postEdit){
        this.title = postEdit.getTitle();
        this.content = postEdit.getContent();
    }*/

    // postEditor 호출 메서드
   /* public PostEditor.PostEditorBuilder toEditor(){
        return PostEditor.builder()
                .title(title)
                .content(content);
    }*/



    // 수정 메서드 2
    /*public void edit(PostEditor postEditor){
        title = postEditor.getTitle();
        content = postEditor.getContent();
    }*/


    // 엔티티에 getter 를 만드는 경우 서비스 정책을 포함시키지 말 것!!! 절대!!!!
    // ex) title 10 글자로 제한
    // 응답 전용 클래스 분리 추천(서비스 정책에 맞는) > ex) PostResponse

    /*public String getTitle(){
        return this.title.substring(0, 10);
    }*/
}
