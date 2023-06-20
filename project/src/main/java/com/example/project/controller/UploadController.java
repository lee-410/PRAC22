package com.example.project.controller;

import com.example.project.DTO.UploadResultDTO;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class UploadController {

    @GetMapping("/upload")
    public ModelAndView uploadForm() {
        ModelAndView modelAndView = new ModelAndView("upload");
        return modelAndView;
    }

    @Value("${com.example.upload.path}") // application.properties에서 @Value로 값을 받아온 후 uploadPath에 데이터 주입
    private String uploadPath; //이미지가 저장될 경로

    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDTO>> uploadFile(MultipartFile[] uploadFiles){ //배열로 받은 이유는 이미지가 여러개일 수 있기 때문. uploadFiles는 내가 업로드한 이미지 그 자체!! (아직 가공되기 전!)

        List<UploadResultDTO> resultDTOList = new ArrayList<>(); //업로드 결과 정보를 담을 리스트 객체 생성

        for (MultipartFile uploadFile : uploadFiles) {

            // 이미지 파일만 업로드 가능->이미지인지 확인하고 아니면 403 Forbidden반환
            if(uploadFile.getContentType().startsWith("image") == false){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }//getContentType()는 업로드된파일의 MIME타입(확장자)을 확인, startsWith는 그 문자열이 image로 시작하는지 확인(MIME ex. image/jpeg)

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

                Thumbnailator.createThumbnail(savePath.toFile(),thumbnailFile,200,200);// 섬네일 생성

                resultDTOList.add(new UploadResultDTO(fileName,uuid,folderPath));
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        //Spring Framework에서 HTTP응답을 반환하는 코드이다.
        //ResponseEntity는 HTTP응답을 나타내는 클래스로, 응답의 본문을 데이터와 함께 응답상태코드, 헤더 등을 포함할 수 있다.
        /* resultDTOList 객체를 응답 본문으로 설정하고, 상태코드를 HttpStatus.OK로 설정하여 성공적인 응답을 반환한다.
           API에서 반환되는 데이터를 담은 객체이며, 클라이언트에게 전달할 정보를 담고 있다. */
        return new ResponseEntity<>(resultDTOList, HttpStatus.OK);
    }

    @GetMapping("/display")
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
            /* Files.probeContetType을 사용하여 파일으 컨텐츠 유형을 결정하고, 이를 응답 헤더에 추가한다. 컨텐츠 유형은 반환되는 파일의 MIME 유형을 나타낸다. */
            header.add("Content-Type", Files.probeContentType(file.toPath()));

            //파일 데이터 처리
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @PostMapping("/removeFile")
    public ResponseEntity<Boolean> removeFile(String fileName){
        String srcFileName = null;

        try {
            srcFileName = URLDecoder.decode(fileName,"UTF-8");
            File file = new File(uploadPath + File.separator + srcFileName);

            boolean result = file.delete();

            File thumbnail = new File(file.getParent(),"s_" + file.getName());

            result = thumbnail.delete();

            return new ResponseEntity<>(result,HttpStatus.OK);

        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
        }
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