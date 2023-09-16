package com.example.project.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
//import org.springframework.security.crypto.password.PasswordEncoder;

@Entity(name = "Feed")
@NoArgsConstructor
@Getter
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    private String author;
    private String roles;
    private LocalDateTime uploadTime;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Feed(Long postId, String content, Member member) {
        this.postId = postId;
        this.content = content;
        this.member = member;
        this.author = member.getUserid();
        this.roles = member.getRoles();
        this.uploadTime = LocalDateTime.now();
    }

    public String getFormattedUploadTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd a hh:mm");
        return uploadTime.format(formatter);
    }

}

