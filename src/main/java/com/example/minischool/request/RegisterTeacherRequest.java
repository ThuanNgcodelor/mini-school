package com.example.minischool.request;

import lombok.Data;

@Data
public class RegisterTeacherRequest {
    private String phone;
    private String email;
    private String fullName;
    private String password;
    private String inviteCode;
    private String specialization;
}
