package com.example.project.controller;

import com.example.project.DTO.UploadResultDTO;
import com.example.project.Entity.Feed;
import com.example.project.Entity.Member;
import com.example.project.repository.FeedRepository;
import com.example.project.repository.UserRepository;
import com.example.project.service.FileDisplayService;
import com.example.project.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;


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
    private final FileDisplayService fileDisplayService;

    @Autowired
    public UploadController(FileUploadService fileUploadService, FileDisplayService fileDisplayService) {
        this.fileUploadService = fileUploadService;
        this.fileDisplayService = fileDisplayService;
    }

    @PostMapping(value = "/uploadImages")
    public ResponseEntity<List<UploadResultDTO>> uploadImages(@RequestParam("uploadFiles") MultipartFile[] uploadFiles) {
        List<UploadResultDTO> result = fileUploadService.uploadFiles(uploadFiles);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(@RequestParam String fileName, @RequestParam String size) {
        return fileDisplayService.getFile(fileName, size);
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