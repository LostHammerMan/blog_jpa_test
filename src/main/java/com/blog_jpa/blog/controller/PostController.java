package com.blog_jpa.blog.controller;

import com.blog_jpa.blog.domain.entity.Post;
import com.blog_jpa.blog.dto.request.PostCreate;
import com.blog_jpa.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    // SSR -> jsp, thymeleaf, mustache
    //  - 서버에서 html rendering

    // SPA -> vue, react
    //  - javascript + 서버와의 통신은 API(json)

    // http Method
    // - get, post, put, delete, options, head, trace, connect

    // post -> 200, 201
    // post 요청은 일반적으로 void
    // but 응답데이터를 달라고 클라이언트에서 요청하는 경우
    @PostMapping("/post2")
//    public Post post2(@RequestBody @Valid PostCreate request) throws Exception {
//    public Map<String, Long> post2(@RequestBody @Valid PostCreate request) throws Exception {
    public void post2(@RequestBody @Valid PostCreate request) throws Exception {
        // repository.save(params)
        log.info("post2 called..............");

        // DB 에 저장한 형태를 그대로 내려줌
        // case1 . 저장한 데이터 entity -> response 로 응답하기
//        return postService.write(request);

        // case2 . 저장한 데이터의 primary_id -> response 로 응답하기
        //  client 에서는 수신한 id 를 글 조회 API를 통해 글 데이터를 수신받음
//        Long postId = postService.write(request);
//        return Map.of("postId", postId);

        // case3 . 응답이 필요없는 경우 -> 클라이언트에서 모든 post(글) 데이터 context 잘 관리
        // Bad case. 서버에서 반드시 이렇게 할겁니다 fix
        // -> 서버에서 유연하게 대응할 수 있도록 코드 짜기
        postService.write(request);

    }

    // 단건 조회
    @GetMapping("/posts/{postId}")
    public Post getPost(@PathVariable(name = "postId") Long id){
//        Optional<Post> findPost = postService.getPost(id);
        Post findPost = postService.get(id);
        return findPost;
    }

    // Rss 발급 서비스
    @GetMapping("/posts/{postId}/rss")
    public Post getRss(@PathVariable(name = "postId") Long id){
//        Optional<Post> findPost = postService.getPost(id);
        Post findPost = postService.getRss(id);

        // getTitle 메서드에 따라 10글자로 제한되는 문제 발생

        return findPost;
    }


    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Valid String params) throws Exception {

        // 데이터를 검증하는 이유
        /* 1. client 개발자가 깜박할 수 있음. 실수로 값을 안보낼 수 있음
           2. client bug 로 값 누락 가능
           3. 외부인이 값을 임의로 조작 가능
           4. Db 에 값을 저장시 의도치 않은 오류 발생 가능
           5. 서버 개발자의 편안함을 위해
        * */
        log.info("params = {}", params.toString());
        /*String title = params.get("title");
        String content = params.get("content");*/
        // title 검증
       /* String title = params.getTitle();
        if (title == null || title.equals("")){
            // 에러 --> spring validation 사용
            // 아래와 같은 검증 --> 1. 노가다
            // 개발팁 -> 2. 무언가 3번이상 반복작업을 할때 내가 뭔가 실수한 것인지 의심
            // 3. 누락 가능성 검토
            // 4. 생각보다 검증해야 할게 많음
            // 5. 개발자 스럽지 않음
            throw new Exception("타이틀 값이 없음");
        }

        // content 검증
        String content = params.getContent();
        if (content == null || content.equals("")){
            // 에러
            throw new Exception("컨텐트 값이 없음");

        }*/
        /*if (result.hasErrors()){
            List<FieldError> errors = result.getFieldErrors();
            // title 에 대한 에러처리
            FieldError firstFieldError = errors.get(0);
            String fieldName = firstFieldError.getField(); // title
            String errorMessage = firstFieldError.getDefaultMessage(); // errorMessage

            // content 에 대한 에러처리의 경우 --> for 문 돌려서 처리도 가능
            // controllerAdvice 로 처리
            FieldError secondFieldError = errors.get(1);
            String fieldName2 = firstFieldError.getField(); // title
            String errorMessage2 = firstFieldError.getDefaultMessage(); // errorMessage

            Map<String, String> error = new HashMap<>();
            error.put(fieldName, errorMessage);
            error.put(fieldName2, errorMessage2);
            return error;
        }*/

        /* 위와 같은 검증 문제점
        *  1. 매번 메서드마다 값 검증
        *    - 개발자가 까먹을 수 있음
        *    - 검증 부분에서 버그 발생 여지
        *
        *  2. 응답값에 HashMap -> 응답 클래스를 만들어주는게 좋음
        *  3. 여러개의 에러처리 힘듦
        *  4. 세 번 이상의 반복적인 작업은 피해야 함 --> 코드, 개발에 관한 모든 것
        *   --> 자동화 할 수 있는 방안 생각해야 함 -->
        * */
        return Map.of();
    }


}
