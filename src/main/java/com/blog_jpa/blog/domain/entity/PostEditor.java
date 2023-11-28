package com.blog_jpa.blog.domain.entity;

import lombok.Builder;
import lombok.Getter;

// 엔티티와 같은 영역에 추가함
@Getter
public class PostEditor {

    // 수정할 수 있는 필드에 대해서만
    private final String title;
    private final String content;

    // : 생성자로 체크하는 방법
    public PostEditor(String title, String content) {
            this.title = title;
            this.content = content;

//        this.title = title != null ? title : this.getTitle();
//        this.content = content != null ? content : this.getContent();
    }
    public static PostEditorBuilder builder() {
        return new PostEditorBuilder();
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public static class PostEditorBuilder {
        private String title;
        private String content;

        PostEditorBuilder() {
        }

        public PostEditorBuilder title(String title) {
            this.title = title;
            return this;
        }

        public PostEditorBuilder content(String content) {
            this.content = content;
            return this;
        }

        public PostEditor build() {
            return new PostEditor(this.title, this.content);
        }

        public String toString() {
            return "PostEditor.PostEditorBuilder(title=" + this.title + ", content=" + this.content + ")";
        }
    }

}
