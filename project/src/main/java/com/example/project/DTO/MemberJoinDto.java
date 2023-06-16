package com.example.project.DTO;

public class MemberJoinDto {

    private String user_id;
    private String user_pw;

    public String getUserid() {
        return user_id;
    }

    public void setUserid(String userid) {
        this.user_id = userid;
    }

    public String getPw() {
        return user_pw;
    }

    public void setPw(String pw) {
        this.user_pw = pw;
    }
}
