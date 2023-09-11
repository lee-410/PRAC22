package com.example.project.repository;

import com.example.project.Entity.Profile;
import com.example.project.Entity.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
    List<ProfileImage> findByMemberUserid(String userid);

}
