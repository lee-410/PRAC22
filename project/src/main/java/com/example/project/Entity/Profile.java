package com.example.project.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "Profile")
@NoArgsConstructor
@Getter
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String user_id;

    private String roles;

    private String image_path; //이거 업데이트되면 feed에서도 갱신같이 되게!

    public Profile(Long id, String user_id, String roles, String image_path) {
        this.id = id;
        this.user_id = user_id;
        this.roles = roles;
        this.image_path = image_path;
    }

}
