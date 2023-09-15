package com.example.project.service;


import com.example.project.DTO.ProfileDTO;
import com.example.project.DTO.UploadResultDTO;
import com.example.project.Entity.Feed;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProfileService {
    List<ProfileDTO> uploadProfile(MultipartFile[] uploadFiles,Authentication authentication);
    String uploadIntro(String introductionText, Authentication authentication);
    List<ProfileDTO> getUploadedImagesByUserId(String userId);
   // void updateProfile();
   List<Feed> feedGetList();
   List<Feed> getFeedByPostId(Long postId);
}
