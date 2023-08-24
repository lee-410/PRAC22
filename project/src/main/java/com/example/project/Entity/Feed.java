package com.example.project.Entity;

import jakarta.persistence.*;


import java.time.LocalDateTime;
//import org.springframework.security.crypto.password.PasswordEncoder;


@Entity(name = "Feed")
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

    public Feed(Long post_id, String title, String author, String roles, LocalDateTime uploadTime, String content, String image_path) {
        this.post_id = post_id;
        this.title = title;
        this.author = author;
        this.roles = roles;
        this.uploadTime = uploadTime;
        this.content = content;
        this.image_path = image_path;
    }

    public Feed() {}

    public Long getPost_id() {
        return post_id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getRoles() {
        return roles;
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

    public void setPost_id(Long post_id) {
        this.post_id = post_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}

