package com.example.project.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

//데이터 전송을 위한 객체, 데이터 구조를 정의!!
@Data
public class UploadResultDTO {

    private String fileName;

    private String uuid;

    private String folderPath;

    public String getImageURL(){
        try {
            return URLEncoder.encode(folderPath+"/" +uuid +"_" + fileName,"UTF-8");

        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return "";
    }


    public String getThumbnailURL(){
        try {
            return URLEncoder.encode(folderPath + "/s_" +uuid + "_" +fileName,"UTF-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        return "";
    }

    public UploadResultDTO(String fileName, String uuid, String folderPath) {
        this.fileName = fileName;
        this.uuid = uuid;
        this.folderPath = folderPath;
    }

    public UploadResultDTO() {
        // 기본 생성자
    }
}