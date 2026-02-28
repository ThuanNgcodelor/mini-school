package com.example.minischool.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String phone;
    private String password;
}
