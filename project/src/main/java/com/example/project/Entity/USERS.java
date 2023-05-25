package com.example.project.Entity;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;
import java.time.LocalDateTime;
//
//@ToString
//@Getter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "USERS")
@Entity(name = "USERS")
@Data
public class USERS {

    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) //1씩 자동증가
    private Long user_no;

    @Column(length = 200, nullable = false) //NOT NULL, 200글자 제한(string에서만)
    private String user_id;

    @Column(length = 200, nullable = false)
    private String user_pw;

    @Column(length = 200, nullable = false)
    private String user_name;

    @Column(length = 200, nullable = false)
    private String user_auth;

    @Column
    private LocalDate append_date;

}
