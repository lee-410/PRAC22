package com.example.project.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity //Spring Security를 활성화
public class SpringSecurityConfig implements WebMvcConfigurer {

    @Bean //비밀번호 인코딩 처리를 하기 위한 'PasswordEncoder'빈을 정의
    public PasswordEncoder passwordEncoder() {
        //보안상 비밀번호 안전하게 저장하기 위해 BCrypt알고리즘 사용
        return new BCryptPasswordEncoder();
    }

    @Bean //시큐리티 필터 체인 구성 설정
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { //HttpSecurity 객체 사용해서 보안규칙 정의
        http
                .authorizeHttpRequests(request -> request
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("/", "/images/**", "/view/join", "/auth/join","/getImagesFromUpload","/getContentFromUpload").permitAll()
                        .anyRequest().authenticated()
                )//http.authorizeHttpRequests()는 HTTP요청에 대한 권한 구성
                .formLogin(login -> login
                        .loginPage("/view/login")
                        .loginProcessingUrl("/login-process")
                        .usernameParameter("userid")
                        .passwordParameter("pw")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login.html?error=true")
                        .permitAll()
                )//http.formLogin()은 폼 기반 로그인을 활성화
                .logout(withDefaults()); //http.logout()은 로그아웃 구성 활성화

        return http.build();
    }
}
