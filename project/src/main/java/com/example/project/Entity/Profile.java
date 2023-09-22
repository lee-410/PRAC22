package com.example.project.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Profile")
@NoArgsConstructor
@Getter
@Setter
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long profileID;

    @Column(name = "userId")
    private String userID;

    private String roles;

    @Column(length = 50)
    private String introduction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Profile(Long id, String introduction, Member member) {
        this.profileID = id;
        this.userID = member.getUserid();
        this.roles = member.getRoles();
        this.introduction = introduction;
        this.member = member;
    }

}
