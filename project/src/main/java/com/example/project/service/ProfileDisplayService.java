package com.example.project.service;

import com.example.project.Entity.Feed;
import org.springframework.http.ResponseEntity;

public interface ProfileDisplayService {
    ResponseEntity<byte[]> getFile(String fileName, String size);

}
