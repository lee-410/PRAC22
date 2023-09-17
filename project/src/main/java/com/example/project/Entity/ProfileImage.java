package com.example.project.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "ProfileImage")
@NoArgsConstructor
@Getter
public class ProfileImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long imageId;

    private String userId;
    private String fileName;
    private String uuid;
    private String folderPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public ProfileImage(Long imageId, String userId, String fileName, String uuid, String folderPath, Member member) {
        this.imageId = imageId;
        this.userId = member.getUserid();
        this.fileName = fileName;
        this.uuid = uuid;
        this.folderPath = folderPath;
        this.member = member;
    }
}
