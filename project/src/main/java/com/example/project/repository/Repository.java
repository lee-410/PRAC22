package com.example.project.repository;

import com.example.project.Entity.USERS;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository<USERS, Long> {
}
