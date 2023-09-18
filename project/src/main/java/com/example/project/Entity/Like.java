//package com.example.project.Entity;
//
//
//import jakarta.persistence.*;
//import lombok.Builder;
//import lombok.Data;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Entity(name = "Like")
//@Data
//@NoArgsConstructor
//public class Like {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    private Long LikeId;
//
//    private String userId; // 나
//    private Long postId; //피드 번호
//    private Integer feedLikeCnt = 0; //좋아요 수
//
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Feed feed;
//
//    private Member member;
//
//    @Builder
//    public Like(Long likeId, String userId, Long userFeedId, Long postId, Integer feedLikeCnt, Feed feed, Member member) {
//        this.userId = member.getUserid();
//        this.postId = feed.getPostId();
//        this.feedLikeCnt = feedLikeCnt;
//    }
//}
