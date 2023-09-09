package com.example.project.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
public class ProfileDTO {

    private String fileName;

    private String uuid;

    private String folderPath;

}
