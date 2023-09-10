package com.example.project.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Profile")
@NoArgsConstructor
@Getter
@Setter
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String user_id;

    private String roles;

    private String image_path; //이거 업데이트되면 feed에서도 갱신
    @Column(length = 50)
    private String introduction; //controller에서 길이제한 예외처리필요>잘려서저장됨

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Profile(Long id, String user_id, String roles, String image_path, String introduction, Member member) {
        this.id = id;
        this.user_id = member.getUserid();
        this.roles = member.getRoles();
        this.image_path = image_path;
        this.introduction = introduction;
        this.member = member;
    }

}
