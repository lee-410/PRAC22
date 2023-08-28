package com.example.project.controller;

import com.example.project.Entity.Member;
import com.example.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
@RequestMapping("/join")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping
    public ModelAndView search() {
        ModelAndView modelAndView = new ModelAndView("join");
        return modelAndView;
    }

    @PostMapping
    public Member createuser(@RequestBody Member users) {
        Member newUSERS = userRepository.save(users);
        //return users.getUser_name() + "님의 회원가입이 완료되었습니다.";
        return newUSERS;
    }

    @GetMapping("/user")
    public Member findUser(@RequestParam Long user_no) {
        Optional<Member> users = userRepository.findById(user_no);
        return users.get();
    }

    @PutMapping("/update")
    public Optional<Member> changeInfo(@RequestParam Long user_no, @RequestBody Member users) {
        Optional<Member> updateUser = userRepository.findById(user_no);
        updateUser.ifPresent(selectUser -> {
            selectUser.setUserid(users.getUserid());
            selectUser.setPw(users.getPw());

            userRepository.save(selectUser);
        });
        return updateUser;
    }

    @DeleteMapping("/user")
    public Optional<Member> deleteUser(@RequestParam Long user_no) {
        Optional<Member> newUSER = userRepository.findById(user_no);
        newUSER.ifPresent(selectUser -> {
            userRepository.delete(selectUser);
        });
        return newUSER;
    }
}
