package com.example.project.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
public class UploadResultDTO { //브라우저에서 파일 저장 처리가 간단할 수 있도록 클래스와 객체를 구성해 처리한다.
    private String fileName;
    private String uuid;
    private String folderPath;

    //UploadResultDTO는 실제 파일과 관련된 모든 정보를 가지는데 나중에 전체 경로가 필요한 경우를 대비해 getImageURL()을 제공한다.
    public String getImageURL() {
        try {
            return URLEncoder.encode(folderPath+"/"+uuid+fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
