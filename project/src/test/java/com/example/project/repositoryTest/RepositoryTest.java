package com.example.project.repositoryTest;

import com.example.project.Entity.USERS;
import com.example.project.repository.Repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class RepositoryTest {
    @Autowired
    Repository repository;

    @Test
    public void Insert() {
        IntStream.rangeClosed(1,10).forEach(i -> {
            USERS users = USERS.builder()
                    .usersText("SAMPLEEE..."+i)
                    .build(); //create!
            repository.save(users);
        });
    }
}
