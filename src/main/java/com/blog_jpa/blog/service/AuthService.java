package com.blog_jpa.blog.service;

import com.blog_jpa.blog.crypto.PasswordEncoder;
import com.blog_jpa.blog.crypto.ScryptPasswordEncoder;
import com.blog_jpa.blog.domain.entity.User;
import com.blog_jpa.blog.dto.request.Login;
import com.blog_jpa.blog.dto.request.SignUpDto;
import com.blog_jpa.blog.exception.AlreadyExistUserException;
import com.blog_jpa.blog.exception.InvalidSigningInformation;
import com.blog_jpa.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;

    // 로컬인 경우 PlainPasswordEncoder, 운영서버의 경우 ScryptPasswordEncoder
    private final PasswordEncoder passwordEncoder;

    // 로그인
    @Transactional
    public Long signIn(Login login) {

        // DB 에서 유저정보 확인
//        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
//                .orElseThrow(() -> new InvalidSigningInformation());

        // 세션 발급
//        Session session = user.addSession();

        // 암호화된 비밀번호 있을 때 로그인 처리
        User findUser = userRepository.findByEmail(login.getEmail())
                .orElseThrow(() -> new InvalidSigningInformation());

        // 패스워드 인코더 -> 중복되므로 따로 컴포넌트로 생성
//        SCryptPasswordEncoder encoder = new SCryptPasswordEncoder(
//                16,
//                8,
//                1,
//                32,
//                16
//        );
//        PasswordEncoder passwordEncoder = new PasswordEncoder();

        // 일치 여부 확인
//        boolean matches = encoder.matches(login.getPassword(), findUser.getPassword());

        boolean matches = passwordEncoder.matches(login.getPassword(), findUser.getPassword());

        if (!matches){
            throw new InvalidSigningInformation();
        }

        return findUser.getId();
    }

    // 회원 가입
    @Transactional
    public void signUp(SignUpDto signUpDto) {

        // 중복 유저 여부 확인
        Optional<User> findUser = userRepository.findByEmail(signUpDto.getEmail());

        if (findUser.isPresent()){
            throw new AlreadyExistUserException();
        }

//        SCryptPasswordEncoder encoder = new SCryptPasswordEncoder(16,
//                8,
//                1,
//                32,
//                64);
//        String encryptPassword = encoder.encode(signUpDto.getPassword());
//        PasswordEncoder passwordEncoder = new PasswordEncoder();
        String encryptPassword = passwordEncoder.encrypt(signUpDto.getPassword());

        // 엔티티로 변환
        User user = User.builder()
                .name(signUpDto.getName())
                .password(encryptPassword)
                .email(signUpDto.getEmail())
                .build();

        userRepository.save(user);
    }
}
