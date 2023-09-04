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
public class FileDisplayServiceImpl implements FileDisplayService{
    @Value("${com.example.upload.path}") // application.properties에서 @Value로 값을 받아온 후 uploadPath에 데이터 주입
    private String uploadPath; //이미지가 저장될 경로

    @Override
    public ResponseEntity<byte[]> getFile(String fileName, String size){
        ResponseEntity<byte[]> result = null;

        try {
            //UTF-8인코딩을 사용하여 fileName을 디코딩한다. fileName에 포함된 URL인코딩된 문자를 처리하기 위한 것이다.
            //디코딩하면 "~/이미지.jpg"와 같이 변경된다.
            String srcFileName = URLDecoder.decode(fileName,"UTF-8");

            File file = new File(uploadPath + File.separator + srcFileName);

            // size=1일때는 데이터 전송할때 속도가 느릴 수 있기 때문에 if절안의 코드로 전처리해서 데이터를 전송하도록 한다.
            if(size != null && size.equals("1")){
                file = new File(file.getParent(),file.getName().substring(2));
            }//참 일때, 동일한 상위 디렉토리를 가진 새로운 File 객체를 생성하고 원본 파일이름의 첫 두문자를 제외한 이름으로 수정한다.

            HttpHeaders header = new HttpHeaders();

            //MIME타입 처리
            /* Files.probeContentType을 사용하여 파일으 컨텐츠 유형을 결정하고, 이를 응답 헤더에 추가한다. 컨텐츠 유형은 반환되는 파일의 MIME 유형을 나타낸다. */
            header.add("Content-Type", Files.probeContentType(file.toPath()));

            //파일 데이터 처리
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
}
