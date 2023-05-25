package com.example.project.repositoryTest;

import com.example.project.Entity.USERS;
import com.example.project.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired //Auto어노테이션으로 spring IOC가 Bean의 의존성을 알아서 주입
    UserRepository userRepository;

    @Test
    public void create() {
        USERS users = new USERS();

        users.setUser_id("xofks410");
        users.setUser_pw("1234");
        users.setUser_name("taeran");
        users.setUser_auth("USER");
        users.setAppend_date(LocalDate.parse("2023-05-25"));

        USERS newUSERS = userRepository.save(users);
    }

    @Test
    public void read() { //특정값에 의한 정보를 읽는 것이 목적
        Optional<USERS> users = userRepository.findById(1L);

        users.ifPresent(selectUser -> {
            System.out.println(selectUser.getUser_name());
            System.out.println(selectUser.getUser_id());
        });
    }

    @Test
    public void update() {
        Optional<USERS> users = userRepository.findById(1L);
        users.ifPresent(selectUser -> {
            selectUser.setUser_name("이태란");
            selectUser.setUser_id("tearan1234");
            userRepository.save(selectUser);
        });
    }
}
