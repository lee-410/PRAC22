package com.example.project.DTO;

import com.example.project.Entity.Feed;
import com.example.project.Entity.Member;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

//데이터 전송을 위한 객체, 데이터 구조를 정의!!
@Data
public class UploadResultDTO {

    private Long imageId;
    private String fileName;

    private String uuid;

    private String folderPath;
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Feed feed;

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

    public UploadResultDTO(Long imageId, String fileName, String uuid, String folderPath) {
        this.imageId = imageId;
//        this.postId = feed.getPostId();
        this.fileName = fileName;
        this.uuid = uuid;
        this.folderPath = folderPath;
    }

    public UploadResultDTO() {
        // 기본 생성자
    }

}