package com.example.project.repositoryTest;


import com.example.project.Entity.Feed;
import com.example.project.Entity.Member;
import com.example.project.repository.FeedRepository;
import com.example.project.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
public class UploadControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;

    @Test
    public void testUploadText() throws Exception {
        String content = "Test content!!";

        Member member = new Member();
        member.setUserid("ean");
        member.setPw("1234");
        member.setRoles("USER");
        member = userRepository.save(member);

        mockMvc.perform(MockMvcRequestBuilders.post("/uploadText/{userid}", member.getUserid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"" + content + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(content));

        //log.info("TestUploadText completed successfully.");
        System.out.println("TestUploadText completed successfully2.");
        // 이후에 DB에 해당 content가 저장되었는지 확인하는 로직 추가 가능
    }
}

