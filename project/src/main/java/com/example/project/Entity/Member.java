package com.example.project.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
//import org.springframework.security.crypto.password.PasswordEncoder;

//DB에서 사용되는 데이터의 표현, DB와 관련된 작업처리
@Entity(name = "MEMBER")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userid; // =author

    private String pw;

    private String roles;

    private LocalDateTime createTime;

    private Member(Long id, String userid, String pw, String roleUser,LocalDateTime createTime) {
        this.id = id;
        this.userid = userid;
        this.pw = pw;
        this.roles = roleUser;
        this.createTime = createTime;
    }

    protected Member() {}

//    public static Member createUser(String userId, String pw, PasswordEncoder passwordEncoder) {
//        return new Member(null, userId, passwordEncoder.encode(pw), "USER");
//    }

    public Long getId() {
        return id;
    }

    public String getUserid() {
        return userid;
    }

    public String getPw() {
        return pw;
    }

    public String getRoles() {
        return roles;
    }

    public LocalDateTime getCreateTime() { return createTime; }
}