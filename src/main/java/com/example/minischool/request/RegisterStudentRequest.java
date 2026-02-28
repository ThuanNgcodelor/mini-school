package com.example.minischool.request;

import lombok.Data;

@Data
public class RegisterStudentRequest {
    private String phone;
    private String fullName;
    private String password;
    private String classCode;
    private String parentName;
    private String parentPhone;
}
