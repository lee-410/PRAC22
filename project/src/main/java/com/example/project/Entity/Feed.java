package com.example.project.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
//import org.springframework.security.crypto.password.PasswordEncoder;

@Entity(name = "Feed")
public class Feed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long post_id;

    private String title;

    private String author; // userid

    private LocalDateTime uploadTime;
    private String content;
    private String image_path;


    public Feed(Long id, Long post_id, String title, String author, LocalDateTime uploadTime, String content, String image_path) {
        this.id = id;
        this.post_id = post_id;
        this.title = title;
        this.author = author;
        this.uploadTime = uploadTime;
        this.content = content;
        this.image_path = image_path;
    }

    protected Feed() {}

    public Long getId() {
        return id;
    }

    public Long getPost_id() {
        return post_id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public String getContent() {
        return content;
    }

    public String getImage_path() {
        return image_path;
    }
}
