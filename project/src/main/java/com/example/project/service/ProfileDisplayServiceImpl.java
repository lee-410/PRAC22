package com.example.project.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.net.URLDecoder;
import java.nio.file.Files;

@Service
public class ProfileDisplayServiceImpl implements ProfileDisplayService {
    @Value("${com.example.profile.path}")
    private String uploadPath;

    @Override
    public ResponseEntity<byte[]> getFile(String fileName, String size){
        ResponseEntity<byte[]> result = null;

        try {
            String srcFileName = URLDecoder.decode(fileName,"UTF-8");

            File file = new File(uploadPath + File.separator + srcFileName);

            if(size != null && size.equals("1")){
                file = new File(file.getParent(),file.getName().substring(2));
            }

            HttpHeaders header = new HttpHeaders();

            header.add("Content-Type", Files.probeContentType(file.toPath()));

            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
}
