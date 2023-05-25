package com.example.project.repository;

import com.example.project.Entity.USERS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<USERS, Long> { //<해당엔티티, 엔티티 PK자료형>

}
