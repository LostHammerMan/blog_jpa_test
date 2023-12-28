package com.blog_jpa.blog.config.security;

import com.blog_jpa.blog.config.handler.Http401Handler;
import com.blog_jpa.blog.config.handler.Http403Handler;
import com.blog_jpa.blog.config.handler.LoginFailHandler;
import com.blog_jpa.blog.domain.entity.User;
import com.blog_jpa.blog.exception.InvalidSigningInformation;
import com.blog_jpa.blog.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.util.logging.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Optional;

@Configuration
@EnableWebSecurity(debug = true)
@Slf4j
public class SecurityConfig { // 스프링 시큐리티 6 부터 extends 가 아닌 Bean 주입 방식으로 변화

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return new WebSecurityCustomizer(){
            @Override
            public void customize(WebSecurity web) {
                web.ignoring().requestMatchers("/favicon.ico", "/error")
                        // h2 db 사용하는 경우
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()
                // 로그인 페이지 모두에게 허용
                // HttpMethod 가 post 인 경우에만
//                    .requestMatchers(HttpMethod.POST,"/auth/login").permitAll()
                    .requestMatchers("/auth/login").permitAll()
                    .requestMatchers("/auth/signUp").permitAll()
                // hasAnyRole : 다수의 role 추가
//                .requestMatchers("/user").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/user").hasRole("USER")
                // 권한 및 역할 체크
//                .requestMatchers("admin")
//                    .access(new WebExpressionAuthorizationManager(
//                            "hasRole('ADMIN') AND hasAuthority('WRITE')"))
                // 그외 인증 필요
                    .anyRequest().authenticated()
                .and()

                .formLogin()
                // html input name

                    .loginPage("/auth/login")
                    // post 로 받아서 검증하는 url
                    .loginProcessingUrl("/auth/login")
                    .defaultSuccessUrl("/") // form 로그인 성공시 이동할 url
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .failureHandler(new LoginFailHandler())
                .and()
                .exceptionHandling(e -> {
                    e.accessDeniedHandler(new Http403Handler());
                    e.authenticationEntryPoint(new Http401Handler()); // 로그인이 필요하다고 요청하는 핸들러

                })
                // 'remember' parameter 와 왔을 떄 활성화
                .rememberMe(rm -> rm.rememberMeParameter("remember")
                        .alwaysRemember(false)
                        .tokenValiditySeconds(2592200)
                        )
//                .userDetailsService(userDetailsService())
                // csrf 설정
                .csrf(
                new Customizer<CsrfConfigurer<HttpSecurity>>() {
                    @Override
                    public void customize(CsrfConfigurer<HttpSecurity> httpSecurityCsrfConfigurer) {
                        httpSecurityCsrfConfigurer.disable();
                    }
                }
        ).build();
    }

    // 로그인 정보 관련
//    @Bean
//    public UserDetailsService userDetailsService(){
//        // 서버가 떠 있는 동안에만 로그인 관련 정보 저장
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        UserDetails user = User.withUsername("hodolman")
//                .password("1234")
//                .roles("ADMIN").build();
//
//        manager.createUser(user);
//        return manager;
//    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User findUser = userRepository.findByEmail(username).orElseThrow(
                        () -> new UsernameNotFoundException(username + " 을 찾을 수 없음")
                );
                return new UserPrincipal(findUser);
            }
        };
    }

    // 암호화 encoder
    @Bean
    public PasswordEncoder encoder(){
        return new SCryptPasswordEncoder(
                16,
                8,
                1,
                32,
                64
        );
    }

    // 암호화 안하는 encoder
//    @Bean
//    public PasswordEncoder encoder2(){
//        return (PasswordEncoder) NoOpPasswordEncoder.getInstance();
//    }
}
