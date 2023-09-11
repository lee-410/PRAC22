package com.example.project.service;

import org.springframework.http.ResponseEntity;

public interface ProfileDisplayService {
    ResponseEntity<byte[]> getFile(String fileName, String size);
}
