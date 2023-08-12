package com.example.project.service;

import com.example.project.Entity.Member;
import com.example.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

//비지니스 로직 처리하는 컴포넌트, Controller와 DB간의 중간계층역할, controller에서 받은 요청을 수행하여 결과 반환
@Service
public class MemberService {
    private final UserRepository repository;

    @Autowired
    public MemberService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<Member> findOne(String userId) {
        return repository.findByUserid(userId);
    }
}
