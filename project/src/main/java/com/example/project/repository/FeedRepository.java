package com.example.project.repository;

import com.example.project.Entity.Feed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {
    //Optional<Feed> findByMemberUserid(String userid);
    List<Feed> findByMemberUserid(String userid);
    Optional<Feed> findByPostId(Long postId);
}
