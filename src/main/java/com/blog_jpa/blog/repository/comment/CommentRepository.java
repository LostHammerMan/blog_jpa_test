package com.blog_jpa.blog.repository.comment;

import com.blog_jpa.blog.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {


}
