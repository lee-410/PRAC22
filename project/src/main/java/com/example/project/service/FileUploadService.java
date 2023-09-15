package com.example.project.service;

import com.example.project.DTO.UploadResultDTO;
import com.example.project.Entity.Feed;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploadService {
    List<UploadResultDTO> uploadFiles(MultipartFile[] uploadFiles);
    List<UploadResultDTO> getUploadedImagesByUserId(String userId);
}
