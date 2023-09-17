package com.example.project.repository;

import com.example.project.Entity.Images;
import com.example.project.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImagesRepository extends JpaRepository<Images, Long> {

    List<Images> findByUserId(String userId);
    List<Images> findAllByOrderByImageIdDesc();

}
