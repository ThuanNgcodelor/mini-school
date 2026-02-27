package com.example.minischool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping({"", "/", "/dashboard"})
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/class/{id}")
    public String classDetails(@PathVariable Long id) {
        return "class-details";
    }

    @GetMapping("/students")
    public String students() {
        return "class-details";
    }

    @GetMapping("/tuition")
    public String tuition() {
        return "tuition";
    }

    @GetMapping("/settings")
    public String settings() {
        return "dashboard";
    }
}
