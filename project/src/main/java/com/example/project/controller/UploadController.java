package com.example.project.controller;

import com.example.project.DTO.UploadResultDTO;
import com.example.project.Entity.Feed;
import com.example.project.Entity.Member;
import com.example.project.repository.FeedRepository;
import com.example.project.repository.UserRepository;
import com.example.project.service.FileDisplayService;
import com.example.project.service.FileUploadService;
import com.example.project.service.FileUploadServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;


@RestController
public class UploadController {
    @GetMapping("/upload")
    public ModelAndView uploadForm() {
        ModelAndView modelAndView = new ModelAndView("upload");
        return modelAndView;
    }

    @Value("${com.example.upload.path}")
    private String uploadPath;

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
    public ResponseEntity<byte[]> getFile(@RequestParam String fileName, @RequestParam(required = false, defaultValue = "1") String size) {
        System.out.println("------------------");
        System.out.println(fileName);
        return fileDisplayService.getFile(fileName, size);
    }

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/uploadText")
    public ResponseEntity<String> uploadText(@RequestBody Map<String, String> requestBody, Authentication authentication) {
        String newContent = requestBody.get("content");
        String userid = authentication.getName();

        Optional<Member> memberList = userRepository.findByUserid(userid);
        if (!memberList.isEmpty()) {
            Member member = memberList.get();

            Feed feed = Feed.builder()
                    .content(newContent)
                    .member(member)
                    .build();

            feedRepository.save(feed);

            return new ResponseEntity<>(newContent, HttpStatus.OK);
        } else {
            // member가 존재하지 않는 경우 처리
            System.out.println("Fail");
            return new ResponseEntity<>("Member not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getContentFromUpload")
    public ResponseEntity<String> getContentFromUpload(Authentication authentication) {
        try {
            String userid = authentication.getName();

            List<Feed> userFeeds = feedRepository.findByMemberUserid(userid);

            if (!userFeeds.isEmpty()) {
                // 사용자의 글들을 문자열로 합쳐서 반환
                String content = userFeeds.stream().map(Feed::getContent).collect(Collectors.joining("\n"));

                // 1. 글이 있을 경우, 내용을 반환한다.
                return ResponseEntity.ok(content);
            } else {
                // 글이 없는 경우에 대한 처리를 여기에 추가
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // 예외 발생 시에 대한 처리를 여기에 추가
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//    @GetMapping("/getImagesFromUpload")
//    public ResponseEntity<List<UploadResultDTO>> getImagesFromUpload() {
//        // uploadImages 메소드를 호출하여 이미지 업로드 결과를 받아옵니다.
//        List<UploadResultDTO> result = fileUploadService.uploadFiles(/* 업로드할 이미지 파일들 */);
//
//        // 받아온 이미지 업로드 결과를 반환합니다.
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

    @GetMapping("/getImagesFromUpload")
    public ResponseEntity<List<UploadResultDTO>> getImagesFromUpload(Authentication authentication) {

        String userId = authentication.getName();
        List<UploadResultDTO> result = fileUploadService.getUploadedImagesByUserId(userId);

        return new ResponseEntity<>(result, HttpStatus.OK);

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

}