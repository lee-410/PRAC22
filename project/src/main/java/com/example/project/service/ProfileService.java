package com.example.project.service;


import com.example.project.DTO.ProfileDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProfileService {
    List<ProfileDTO> uploadProfile(MultipartFile[] uploadFiles);
}
