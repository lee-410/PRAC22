package com.example.project.repository;

import com.example.project.Entity.Feed;
import com.example.project.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Member, Long> { //<해당엔티티, 엔티티 PK자료형>
    Optional<Member> findByUserid(String userid);
    //List<Member> findByUserid(String userid);


    //JSQL select m from Member m where m.name = ? // name으로 엔티티 찾기 가능
    //@Override
    //Optional<Member> findByName(String name);
}
