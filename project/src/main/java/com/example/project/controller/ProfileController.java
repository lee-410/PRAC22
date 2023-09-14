package com.example.project.controller;

import com.example.project.DTO.ProfileDTO;
import com.example.project.DTO.UploadResultDTO;
import com.example.project.Entity.Images;
import com.example.project.Entity.Member;
import com.example.project.Entity.Profile;
import com.example.project.Entity.ProfileImage;
import com.example.project.repository.ImagesRepository;
import com.example.project.repository.ProfileRepository;
import com.example.project.service.ProfileDisplayService;
import com.example.project.service.ProfileService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ImagesRepository imagesRepository;


    @GetMapping
    public ModelAndView profile(Model model, Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("profile");

        String username = authentication.getName();

        List<Profile> profileList = profileRepository.findByMemberUserid(username);

        if (!profileList.isEmpty()) {
            Profile profile = profileList.get(0);
            String introduction = profile.getIntroduction();
            model.addAttribute("introduction", introduction);
//            model.addAttribute("getUserId", username);
            model.addAttribute("feedItems", profileService.feedGetList());

            List<Images> userImagesEntities = imagesRepository.findByUserId(username);
            List<UploadResultDTO> userImages = new ArrayList<>();

            for (Images images : userImagesEntities) {
                UploadResultDTO dto = new UploadResultDTO(images.getFileName(), images.getUuid(), images.getFolderPath());
                userImages.add(dto);
            }

            // 이미지 DTO 목록을 모델에 추가
            model.addAttribute("uploadResults", userImages);
        }
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

    private final ProfileService profileService;
    private final ProfileDisplayService profileDisplayService;

    @Autowired
    public ProfileController(ProfileService profileService, ProfileDisplayService profileDisplayService) {
        this.profileService = profileService;
        this.profileDisplayService = profileDisplayService;
    }

    @Autowired
    private ProfileRepository profileRepository;

    @PostMapping(value = "/getImageIntro")
    public ResponseEntity<List<ProfileDTO>> uploadImages(@RequestParam("uploadFiles") MultipartFile[] uploadFiles,Authentication authentication) {
        //이미지
        List<ProfileDTO> result = profileService.uploadProfile(uploadFiles, authentication);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/getIntro")
    public ResponseEntity<String> uploadText(@RequestParam("introduction") String introductionText,Authentication authentication) {

        //텍스트
        String intro = profileService.uploadIntro(introductionText,authentication);

        return new ResponseEntity<>(intro, HttpStatus.OK);
    }

    @GetMapping("/putProfile")
    public ResponseEntity<List<ProfileDTO>> putProfile(Authentication authentication) {
        String username = authentication.getName();
        List<ProfileDTO> profileList = profileService.getUploadedImagesByUserId(username);

        return new ResponseEntity<>(profileList, HttpStatus.OK);

    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(@RequestParam String fileName, @RequestParam(required = false, defaultValue = "1") String size) {
        return profileDisplayService.getFile(fileName, size);
    }


}
