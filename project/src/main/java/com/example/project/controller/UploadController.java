package com.example.project.controller;

import com.example.project.DTO.UploadResultDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class UploadController {
    @Value("${com.example.upload.path}") //application.properties의 변수
    private String uploadPath;

    //배열로 하면 동시에 여러개의 파일 정보를 처리할 수 있으므로 화면에서 여러개의 파일을 동시에 업로드할 수 있다.
    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDTO>> uploadFile(MultipartFile[] uploadFiles) { //업로드 결과를 반환하기 위해 리턴타입 void에서 ResponseEntity로 변경하고, 임지 파일이 아닌 경우 예외 처리 대신 403 Forbidden을 반환하도록 한다. >> 브라우저는 업로드 후 JSON의 배열형태로 결과를 전달받는다.
        List<UploadResultDTO> resultDTOList = new ArrayList<>();
        for (MultipartFile uploadFile : uploadFiles) {

            //이미지 파일만 업로드
            //업로드된 확장자가 이미지만 가능하도록 검사한다.
            //파일 확장자 체크 (MultipartFile)에서 제공하는 getContentType()이용하려했으나
            if (uploadFile.getContentType().startsWith("image") == false) {
                //이미지가 아닌 경우 403 Forbidden 반환
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            //실제 파일 이름 IE나 Edge는 전체 경로가 들어오므로
            String originalName = uploadFile.getOriginalFilename();

            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

            //날짜 폴더 생성
            String folderPath = makeFolder();

            //UUID
            // 동일한 이름의 파일이 업로드 된다면 기존 파일을 덮어쓴다.
            // UUID를 이용해 고유한 값을 만들어 사용
            String uuid = UUID.randomUUID().toString();

            //저장할 파일 이름 중간에 "_"를 이용해 구분
            //업로드된 파일을 저장하는 폴더의 용량 >> 년/월/일 폴더를 따로 생성해 파일을 저장한다.
            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;

            Path savePath = Paths.get(saveName);

            try {
                uploadFile.transferTo(savePath); //실제 이미지 저장
                resultDTOList.add(new UploadResultDTO(fileName, uuid, folderPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(resultDTOList, HttpStatus.OK);
    }
    private String makeFolder() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = str.replace("/", File.separator);

        //make folder
        File uploadPatheFolder = new File(uploadPath, folderPath);

        if (uploadPatheFolder.exists() == false) {
            uploadPatheFolder.mkdirs();
        }

        return folderPath;
    }
}


