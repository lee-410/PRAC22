package com.example.project.Entity;

import jakarta.persistence.*;


import java.io.Serializable;
import java.time.LocalDateTime;
import org.springframework.security.crypto.password.PasswordEncoder;


@Entity(name = "Member")
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userid; // 참조

    private String pw;

    private String roles; // 참조

    private LocalDateTime createTime;

    private Member(Long id, String userid, String pw, String roleUser,LocalDateTime createTime) {
        this.id = id;
        this.userid = userid;
        this.pw = pw;
        this.roles = roleUser;
        this.createTime = createTime;
    }

    public Member() {}

    public static Member createUser(String userId, String pw, PasswordEncoder passwordEncoder) {
        return new Member(null, userId, passwordEncoder.encode(pw), "USER", LocalDateTime.now());
    }


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

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}