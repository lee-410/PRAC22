package com.example.project.service;

import org.springframework.http.ResponseEntity;

public interface FileDisplayService {
    ResponseEntity<byte[]> getFile(String fileName, String size);



}
