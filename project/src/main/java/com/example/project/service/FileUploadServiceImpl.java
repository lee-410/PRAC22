package com.example.project.service;

import com.example.project.DTO.UploadResultDTO;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Value("${com.example.upload.path}") // application.properties에서 @Value로 값을 받아온 후 uploadPath에 데이터 주입
    private String uploadPath; //이미지가 저장될 경로

    @Override
    public List<UploadResultDTO> uploadFiles(MultipartFile[] uploadFiles) {
        List<UploadResultDTO> resultDTOList = new ArrayList<>();

        for (MultipartFile uploadFile : uploadFiles) {

            // 이미지 파일만 업로드 가능->이미지인지 확인하고 아니면 403 Forbidden반환

            if (uploadFile.getContentType().startsWith("image") == false) {
                throw new IllegalArgumentException("Only image files are allowed.");
            } //타입에러나서 변경
//            if(uploadFile.getContentType().startsWith("image") == false){
//                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//            }//getContentType()는 업로드된파일의 MIME타입(확장자)을 확인, startsWith는 그 문자열이 image로 시작하는지 확인(MIME ex. image/jpeg)

            // 실제 파일 이름 IE나 Edge는 전체 경로가 들어오므로
            // 내가 이미지를 올릴때 C드라이브 들어가서 폴더 경유하고 업로드하기 때문에 그 경로까지 이미지에 포함되어있다. 그래서 그 경로를 제거하고 오로지 파일이름만을 얻기위한 처리가 필요하다.
            String originalName = uploadFile.getOriginalFilename();
            //lastIndexOf는 파일이름에서 마지막 디렉토리 구분자의 인덱스를 찾고, 그 다음 인덱스부터 문자열을 자르는 작업을 수행한다.
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

            // 날짜 폴더 생성
            String folderPath = makeFolder();

            //UUID
            String uuid = UUID.randomUUID().toString();

            //이미지 저장
            //File.separator는 / 역할을 하는데, window(\)Linux(/)을 사용하기 때문에 separator를 사용해서 플랫폼상관없이 구분자를 넣을 수 있다.
            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;

            Path savePath = Paths.get(saveName);

            try {
                uploadFile.transferTo(savePath);// 실제 이미지 저장(원본 파일)

                //섬네일 생성 -> 섬네일 파일 이름은 중간에 s_로 시작
                String thubmnailSaveName = uploadPath + File.separator + folderPath + File.separator +"s_" + uuid +"_"+ fileName;

                File thumbnailFile = new File(thubmnailSaveName);

                Thumbnailator.createThumbnail(savePath.toFile(),thumbnailFile,700,700);// 섬네일 생성

                resultDTOList.add(new UploadResultDTO(fileName,uuid,folderPath));
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return resultDTOList;
    }
    private String makeFolder() {

        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = str.replace("/", File.separator);

        // make folder ----
        File uploadPatheFolder = new File(uploadPath,folderPath);

        if(uploadPatheFolder.exists() == false){
            uploadPatheFolder.mkdirs();
        }

        return folderPath;
    }
}

