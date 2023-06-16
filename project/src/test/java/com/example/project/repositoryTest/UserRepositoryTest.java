//package com.example.project.repositoryTest;
//
//import com.example.project.Entity.Member;
//import com.example.project.repository.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Optional;
//
//@SpringBootTest
//public class UserRepositoryTest {
//    @Autowired //Auto어노테이션으로 spring IOC가 Bean의 의존성을 알아서 주입
//    UserRepository userRepository;
//
//    @Test
//    public void create() {
//        Member users = new Member();
//
//        users.setUser_id("xofks410");
//        users.setUser_pw("1234");
//        users.setUser_name("taeran");
//
//        Member newUSERS = userRepository.save(users);
//    }
//
//    @Test
//    public void read() { //특정값에 의한 정보를 읽는 것이 목적
//        Optional<Member> users = userRepository.findById(1L);
//
//        users.ifPresent(selectUser -> {
//            System.out.println(selectUser.getUser_name());
//            System.out.println(selectUser.getUser_id());
//        });
//    }
//
//    @Test
//    public void update() {
//        Optional<Member> users = userRepository.findById(1L);
//        users.ifPresent(selectUser -> {
//            selectUser.setUser_name("이태란");
//            selectUser.setUser_id("tearan1234");
//            userRepository.save(selectUser);
//        });
//    }
//
//    @Test
//    public void delete(){//read()메서드실행->read()메서드가 존재한다면 원하는 데이터를 삭제->저장
//        Optional<Member> users = userRepository.findById(2L);
//        users.ifPresent(selectUser -> { //검색을 원하는 id값을 찾아서 ifPresent값이 존재할때 삭제
//            userRepository.delete(selectUser);
//        });
//
//    }
//}
