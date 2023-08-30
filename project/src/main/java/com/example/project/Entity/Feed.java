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
    private String title;
    private String author;
    private String roles;
    private LocalDateTime uploadTime;
    private String content;
    private String image_path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Feed(Long post_id, String title, String author, String roles, LocalDateTime uploadTime, String content, String image_path, Member member) {
        this.post_id = post_id;
        this.title = title;
        this.author = member.getUserid();
        this.roles = member.getRoles();
        this.uploadTime = LocalDateTime.now();
        this.content = content;
        this.image_path = image_path;
        this.member = member;
    }
}

