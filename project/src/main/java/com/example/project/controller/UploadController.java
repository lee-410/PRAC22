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
    public ResponseEntity<byte[]> getFile(@RequestParam String fileName, @RequestParam String size) {
        return fileDisplayService.getFile(fileName, size);
    }

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/uploadText")
    public ResponseEntity<String> uploadText(@RequestBody Map<String, String> requestBody, Authentication authentication) {
        String newcontent = requestBody.get("content");
        String userid = authentication.getName();

        if (userid != null) {
            Optional<Member> memberOptional = userRepository.findByUserid(userid);
            if (memberOptional.isPresent()) {
                Member member = memberOptional.get();

                FileUploadService fileUploadService = new FileUploadServiceImpl();

                String imagePath = fileUploadService.getImagePath();

                Feed feed = Feed.builder()
                        .title("Title")
                        .content(newcontent)
                        .image_path(imagePath)
                        .member(member)
                        .build();

                feedRepository.save(feed);

                return new ResponseEntity<>(newcontent, HttpStatus.OK);
            } else {
                // member가 존재하지 않는 경우 처리
                System.out.println("Fail");
                return new ResponseEntity<>("Member not found", HttpStatus.NOT_FOUND);
            }
        } else {
            // 로그인하지 않은 경우 처리
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/getContentFromUpload")
    public ResponseEntity<String> getContentFromUpload(Authentication authentication) {
        try {
            String userid = authentication.getName(); // 현재 로그인한 사용자의 userid 가져오기

            // 0. 해당 사용자의 글을 가져온다.
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