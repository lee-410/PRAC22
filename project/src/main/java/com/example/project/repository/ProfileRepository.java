package com.example.project.repository;


import com.example.project.Entity.Member;
import com.example.project.Entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    //Optional<Feed> findByMemberUserid(String userid);
    List<Profile> findByMemberUserid(String userid);

    void deleteByMember(Member member);
}