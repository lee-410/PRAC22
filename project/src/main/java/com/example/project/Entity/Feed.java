package com.example.project.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
//import org.springframework.security.crypto.password.PasswordEncoder;

@Entity(name = "Feed")
@NoArgsConstructor
@Getter
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long post_id;
    private String author;
    private String roles;
    private LocalDateTime uploadTime;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Feed(Long post_id, String author, String roles, LocalDateTime uploadTime, String content, Member member) {
        this.post_id = post_id;
        this.author = member.getUserid();
        this.roles = member.getRoles();
        this.uploadTime = LocalDateTime.now();
        this.content = content;
        this.member = member;
    }
}

