package com.example.project.controller;



import com.example.project.DTO.ProfileDTO;
import com.example.project.DTO.UploadResultDTO;
import com.example.project.Entity.Feed;
import com.example.project.Entity.Images;
import com.example.project.Entity.ProfileImage;
import com.example.project.repository.FeedRepository;
import com.example.project.repository.ImagesRepository;
import com.example.project.repository.ProfileImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
public class IndexController {

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private ImagesRepository imagesRepository;

    @Autowired
    private ProfileImageRepository profileImageRepository;

    @GetMapping("/")
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView("index");

        //contents
        List<Feed> feedItems = feedRepository.findAll();
        Collections.reverse(feedItems);
        model.addAttribute("feedItems", feedItems);

        //images
        List<Images> userImagesEntities = imagesRepository.findAllByOrderByImageIdDesc();

        List<UploadResultDTO> userImages = new ArrayList<>();

        for (Images images : userImagesEntities) {
            UploadResultDTO dto = new UploadResultDTO(images.getImageId(), images.getFileName(), images.getUuid(), images.getFolderPath());
            dto.setPostId(images.getFeed().getPostId());
            userImages.add(dto);
        }

        model.addAttribute("uploadResults", userImages);

        //profile image
        List<ProfileImage> profileImagesEntities = profileImageRepository.findAll();

        List<ProfileDTO> profileImages = new ArrayList<>();

        for (ProfileImage profileImage : profileImagesEntities) {
            ProfileDTO dto = new ProfileDTO(profileImage.getUserID(), profileImage.getFileName(), profileImage.getUuid(), profileImage.getFolderPath());
            profileImages.add(dto);
        }
        model.addAttribute("profileImages",profileImages);
        log.info("profileImages: {}", profileImages);

        return modelAndView;
    }

    @GetMapping("/userInfoId")
    public String userInfoId(Authentication authentication) {
        String userId = authentication.getName();
        return userId;
    }


}
