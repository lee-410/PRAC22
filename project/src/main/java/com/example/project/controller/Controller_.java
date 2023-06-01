package com.example.project.controller;



import com.example.project.Entity.USERS;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class Controller_ {

    @GetMapping("/upload")
    public ModelAndView uploadForm() {
        ModelAndView modelAndView = new ModelAndView("upload");
        return modelAndView;
    }

    @PostMapping("/upload")
    public USERS uploadPost(@RequestBody com.example.project.Entity.USERS users) {
        //SERS newUSERS = userRepository.save(users);
        //return users.getUser_name() + "님의 회원가입이 완료되었습니다.";
        //return newUSERS;
        return null;
    }


    @GetMapping("/profile")
    public ModelAndView profileForm() {
        ModelAndView modelAndView = new ModelAndView("profile");
        return modelAndView;
    }


}
