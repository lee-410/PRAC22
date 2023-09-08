package com.example.project.controller;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class Controller_ {

    @GetMapping("/test")
    public ModelAndView loginPage() {
        ModelAndView modelAndView = new ModelAndView("test");
        return modelAndView;
    }

//    @GetMapping("/profile")
//    public ModelAndView profileForm() {
//        ModelAndView modelAndView = new ModelAndView("profile");
//        return modelAndView;
//    }



}
