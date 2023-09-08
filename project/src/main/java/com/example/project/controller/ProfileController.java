package com.example.project.controller;

import com.example.project.Entity.Member;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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


}
