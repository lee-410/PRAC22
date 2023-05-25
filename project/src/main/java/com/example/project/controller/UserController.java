package com.example.project.controller;

import com.example.project.Entity.USERS;
import com.example.project.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/index")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/join")
    public String join(@RequestBody USERS users) {
        USERS newUSERS = userRepository.save(users);
        return users.getUser_name() + "님의 회원가입이 완료되었습니다.";
    }

    @GetMapping("/user")
    public USERS findUser(@RequestParam Long user_no) {
        Optional<USERS> users = userRepository.findById(user_no);
        return users.get();
    }

    @PutMapping("/user")
    public Optional<USERS> changeInfo(@RequestParam Long user_no, @RequestBody USERS users) {
        Optional<USERS> updateUser = userRepository.findById(user_no);
        updateUser.ifPresent(selectUser -> {
            selectUser.setUser_name(users.getUser_name());
            selectUser.setUser_id(users.getUser_id());
            selectUser.setUser_pw(users.getUser_pw());

            userRepository.save(selectUser);
        });
        return updateUser;
    }
}
