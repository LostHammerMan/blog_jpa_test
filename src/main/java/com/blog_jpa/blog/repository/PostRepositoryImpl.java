package com.blog_jpa.blog.repository;

import com.blog_jpa.blog.domain.entity.Post;
import com.blog_jpa.blog.domain.entity.QPost;
import com.blog_jpa.blog.dto.request.PostSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.blog_jpa.blog.domain.entity.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Post> getListWithQuerydsl(PostSearch postSearch) {

        return jpaQueryFactory.selectFrom(post)
                .limit(postSearch.getSize())
                .offset((long) (postSearch.getPage() - 1) * postSearch.getSize())
                .orderBy(post.id.desc())
                .fetch();
    }
}
