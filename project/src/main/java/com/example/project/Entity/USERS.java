package com.example.project.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity(name = "USERS")
@Data
public class USERS {

    //기본키가 두개 있을 필요없으니까 user_no를 없애고 user_id를 @Id로 기본키 설정하자.
    //자동증가도 굳이? 할 필요 없을 듯
    @Id // PK
    @Column(length = 200, nullable = false) //NOT NULL, 200글자 제한(string에서만)
    private String user_id;

    @Column(length = 200, nullable = false)
    private String user_pw;

    @Column(length = 200, nullable = false)
    private String user_name;

    @Column(length = 200, nullable = false)
    private String user_auth;

    @Column(length = 200, nullable = false)
    private LocalDate append_date;

    @Column(length = 200, nullable = false)
    private String profile;

    @Column(length = 200, nullable = false)
    private String Email;

}
