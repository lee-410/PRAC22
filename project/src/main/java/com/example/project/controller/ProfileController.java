package com.example.project.controller;

import com.example.project.DTO.ProfileDTO;
import com.example.project.Entity.Member;
import com.example.project.Entity.Profile;
import com.example.project.repository.ProfileRepository;
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

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping
    public ModelAndView profile(Model model) {
        ModelAndView modelAndView = new ModelAndView("profile");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        List<Profile> profileList = profileRepository.findByMemberUserid(username);

        if (!profileList.isEmpty()) {
            Profile profile = profileList.get(0);
            String introduction = profile.getIntroduction();
//            String imagePath = profile.getImagePath();
            model.addAttribute("introduction", introduction);
//            model.addAttribute("imagePath", imagePath);
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

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Autowired
    private ProfileRepository profileRepository;

    @PostMapping(value = "/getImageIntro")
    public ResponseEntity<List<ProfileDTO>> uploadImages(@RequestParam("uploadFiles") MultipartFile[] uploadFiles) {
        //이미지
        List<ProfileDTO> result = profileService.uploadProfile(uploadFiles);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/getIntro")
    public ResponseEntity<String> uploadText(@RequestParam("introduction") String introductionText) {

        //텍스트
        String intro = profileService.uploadIntro(introductionText);

        return new ResponseEntity<>(intro, HttpStatus.OK);
    }


    @GetMapping("/putProfile")
    public String putProfile(Authentication authentication) {
        String username = authentication.getName();
        List<Profile> profileList = profileRepository.findByMemberUserid(username);

        if (!profileList.isEmpty()) {
            Profile profile = profileList.get(0);
            String imagePath = profile.getImagePath();
            return imagePath;
        } else {
            return null;
        }
    }
}
