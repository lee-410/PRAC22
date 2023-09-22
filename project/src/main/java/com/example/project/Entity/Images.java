package com.example.project.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Images")
@NoArgsConstructor
@Getter
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long imageId;

    private String userId;

    private String fileName;
    private String uuid;
    private String folderPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Feed feed;

    @Builder
    public Images(Long imageId, String userId, String fileName, String uuid, String folderPath, Feed feed) {
        this.imageId = imageId;
        this.userId = feed.getAuthor();
        this.fileName = fileName;
        this.uuid = uuid;
        this.folderPath = folderPath;
        this.feed = feed;

    }

    public Long getImageId() {
        return this.imageId;
    }



}

