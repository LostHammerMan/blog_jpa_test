package com.blog_jpa.blog.repository;

import com.blog_jpa.blog.domain.entity.Post;
import com.blog_jpa.blog.dto.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getListWithQuerydsl(PostSearch postSearch);
}
