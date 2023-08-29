package com.example.project.controller;

import com.example.project.DTO.UploadResultDTO;
import com.example.project.Entity.Feed;
import com.example.project.Entity.Member;
import com.example.project.repository.FeedRepository;
import com.example.project.repository.UserRepository;
import com.example.project.service.FileUploadService;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
import java.util.*;

//@CrossOrigin(origins="*", allowedHeaders = "*")
@RestController
public class UploadController {
    @GetMapping("/upload")
    public ModelAndView uploadForm() {
        ModelAndView modelAndView = new ModelAndView("upload");
        return modelAndView;
    }

    @Value("${com.example.upload.path}") // application.properties에서 @Value로 값을 받아온 후 uploadPath에 데이터 주입
    private String uploadPath; //이미지가 저장될 경로

    private final FileUploadService fileUploadService;

    @Autowired
    public UploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping(value = "/uploadImages")
    public ResponseEntity<List<UploadResultDTO>> uploadImages(@RequestParam("uploadFiles") MultipartFile[] uploadFiles) {
        List<UploadResultDTO> result = fileUploadService.uploadFiles(uploadFiles);
        return new ResponseEntity<>(result, HttpStatus.OK);
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

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/uploadText/{userid}")
    public ResponseEntity<String> uploadText(@PathVariable String userid, @RequestBody Map<String, String> requestBody) {
        String newcontent = requestBody.get("content");

        Optional<Member> memberOptional = userRepository.findByUserid(userid);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();

            Feed feed = Feed.builder()
                    .title("Title")
                    .content(newcontent)
                    .image_path("Path")
                    .member(member)
                    .build();

            feedRepository.save(feed);

            return new ResponseEntity(newcontent, HttpStatus.OK);
        } else {
            // member가 존재하지 않는 경우 처리
            System.out.println("Fail");
            return new ResponseEntity("Member not found", HttpStatus.NOT_FOUND);
        }
    }

//    @GetMapping("/getContentFromUpload")
//    public ResponseEntity<String> getContentFromUpload() {
//        //0.글 없으면 안돼. 무조건 있어야돼
//        //1. DB에서 저장된 내용을 불러올 수 있도록.
//        return ResponseEntity.ok(uploadedContent);
//    }

//    @GetMapping("/getImagesFromUpload")
//    public ResponseEntity<String> getImagesFromUpload() {
//        //이미지 무조건 있어야 하고 단 1개만.
//        return new ResponseEntity(resultDTOList, HttpStatus.OK);
//    }

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

}