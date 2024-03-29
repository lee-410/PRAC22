package com.example.project.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
public class ProfileDTO {

    private String userID;

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

    public ProfileDTO(String userID, String fileName, String uuid, String folderPath) {
        this.userID = userID;
        this.fileName = fileName;
        this.uuid = uuid;
        this.folderPath = folderPath;
    }

    public ProfileDTO() {

    }
}
