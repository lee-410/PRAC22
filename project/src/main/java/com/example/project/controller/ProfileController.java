package com.example.project.controller;

import com.example.project.DTO.ProfileDTO;
import com.example.project.DTO.UploadResultDTO;
import com.example.project.Entity.*;
import com.example.project.repository.FeedRepository;
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
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ImagesRepository imagesRepository;


    @GetMapping("/{userId}")
    public ModelAndView profile(Model model, Authentication authentication, @PathVariable String userId) {
        ModelAndView modelAndView = new ModelAndView("profile");

        String username = authentication.getName();
        model.addAttribute("username", username);

        if (username.equals(userId)) {
            List<Profile> profileList = profileRepository.findByMemberUserid(username);

            if (!profileList.isEmpty()) {
                Profile profile = profileList.get(0);
                String introduction = profile.getIntroduction();
                model.addAttribute("introduction", introduction);

                // 피드 데이터 내림차순
                List<Feed> feedItems = profileService.feedGetList();
                Collections.reverse(feedItems);
                model.addAttribute("feedItems", feedItems);

                // 이미지 데이터 내림차순
                List<Images> userImagesEntities = imagesRepository.findAllByOrderByImageIdDesc();

                List<UploadResultDTO> userImages = new ArrayList<>();

                for (Images images : userImagesEntities) {
                    UploadResultDTO dto = new UploadResultDTO(images.getImageId(), images.getFileName(), images.getUuid(), images.getFolderPath());
                    userImages.add(dto);
                }

                model.addAttribute("uploadResults", userImages);
            }
        }else {

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
    public ResponseEntity<List<ProfileDTO>> uploadImages(@RequestParam("uploadFiles") MultipartFile[] uploadFiles, Authentication authentication) {
        //이미지
        List<ProfileDTO> result = profileService.uploadProfile(uploadFiles, authentication);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/getIntro")
    public ResponseEntity<String> uploadText(@RequestParam("introduction") String introductionText, Authentication authentication) {

        //텍스트
        String intro = profileService.uploadIntro(introductionText, authentication);

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

    @PostMapping("/like")
    public String feedLikeShow(@RequestParam Boolean isLinked) {

//        Like like = new Like();
//
//        if (isLinked == true) {
//            like.setFeedLikeCnt(like.getFeedLikeCnt() + 1);
//        } else {
//            like.setFeedLikeCnt(like.getFeedLikeCnt() - 1);
//        }

        return "";
    }


}
