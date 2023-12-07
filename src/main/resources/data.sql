## 서버 시작시 User 샘플데이터 주입
INSERT INTO USER(email, name, password, create_at)
VALUES ('333@333', '동강선', '1234', now());