## 비밀번호 암호화

1. 해시
2. 해시 방식

   1. SHA1
   2. SHA256
   3. MD5
   4. 왜 이런걸로 비번 암호화 하면 안되는지
   5. SHA256

3. BCrpyt, Scrypt, Argon2
   1. salt 값

## Permission Evaluator
- ex) '나'의 게시글을 다른 '사용자'가 삭제 하면 안됨
- 현재 로그인한 사용자와 작성 사용자가 동일한 사용자인지 판단하는 도구

## 댓글 api
cf)
 - 게시글
   - POST /posts
   - GET /posts/{postId}

1. 댓글
   (1) body 에 postId 를 넣거나, 쿼리 파라미터로 postId 넘김
   - POST /comments?postId=1
   {
      postId : 1,
      author : ""
      댓글내용 : ""
   }
   
   - 단 : 게시판이 많아지는 경우 관리 문제 발생 -> url 변경필요

   (2)
   - POST /posts/{postId}/comments
   {
     author : "",
     댓글 : "내용'
   }

   - 단 : 쿼리 파라미터가 길어진다는 문제 발생

DELETE /comments/{commentsId}
PATCH /comments/{commentsId}