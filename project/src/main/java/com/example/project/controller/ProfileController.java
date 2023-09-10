package com.example.project.controller;

import com.example.project.DTO.ProfileDTO;
import com.example.project.Entity.Member;
import com.example.project.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping
    public ModelAndView profile() {
        ModelAndView modelAndView = new ModelAndView("profile");
        return modelAndView;
    }

    @GetMapping("/profile_edit")
    public ModelAndView profileEdit() {
        ModelAndView modelAndView = new ModelAndView("profile_edit");
        return modelAndView;
    }

    @GetMapping("/getUserId")
    public String getUserId(Authentication authentication) {
        String userId = authentication.getName();
        return userId;
    }

    @GetMapping("/editGetUserId")
    public String editGetUserId(Authentication authentication) {
        String userId = authentication.getName();
        return userId;
    }

    @Value("${com.example.profile.path}")
    private String uploadPath;

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

//    @PostMapping(value = "/getImageIntro")
//     public ResponseEntity<List<ProfileDTO>> uploadImages(@RequestParam("uploadFiles") MultipartFile[] uploadFiles, @RequestParam("introduction") String introductionText) {
//        //이미지
//        List<ProfileDTO> result = profileService.uploadProfile(uploadFiles);
//
//        //텍스트
//        String intro = profileService.uploadIntro(introductionText);
//
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

    @PostMapping(value = "/getImageIntro")
    public ResponseEntity<List<ProfileDTO>> uploadImages(@RequestParam("uploadFiles") MultipartFile[] uploadFiles) {
        //이미지
        List<ProfileDTO> result = profileService.uploadProfile(uploadFiles);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/getIntro")
    public ResponseEntity<String> uploadText( @RequestParam("introduction") String introductionText) {

        //텍스트
        String intro = profileService.uploadIntro(introductionText);

        return new ResponseEntity<>(intro, HttpStatus.OK);
    }

}
