package com.example.project.controller;



import com.example.project.Entity.USERS;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class Controller_ {

    @GetMapping("/profile")
    public ModelAndView profileForm() {
        ModelAndView modelAndView = new ModelAndView("profile");
        return modelAndView;
    }
    @GetMapping("/upload")
    public ModelAndView uploadForm() {
        ModelAndView modelAndView = new ModelAndView("upload");
        return modelAndView;
    }


}
