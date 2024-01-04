package com.blog_jpa.blog.repository.post;

import com.blog_jpa.blog.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
}
