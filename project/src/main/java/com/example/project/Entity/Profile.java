//package com.example.project.Entity;
//
//import jakarta.persistence.*;
//
//import java.time.LocalDateTime;
//
//@Entity(name = "Profile")
//public class Profile {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String user_id;
//
//    private String roles;
//
//    private String image_path;
//
//    public Profile(Long id, String user_id, String roles, String image_path) {
//        this.id = id;
//        this.user_id = user_id;
//        this.roles = roles;
//        this.image_path = image_path;
//    }
//
//    protected Profile() {}
//
//    public Long getId() {
//        return id;
//    }
//
//    public String getUser_id() {
//        return user_id;
//    }
//
//    public String getRoles() {
//        return roles;
//    }
//
//    public String getImage_path() {
//        return image_path;
//    }
//}
