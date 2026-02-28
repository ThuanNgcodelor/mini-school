package com.example.minischool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Serves Thymeleaf HTML pages.
 * Business logic is in REST controllers (/api/**).
 * Pages use JavaScript fetch() to call the API.
 */
@Controller
public class PageController {

    // ═══════ Auth ═══════
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    // ═══════ Dashboard ═══════
    @GetMapping({"", "/", "/dashboard"})
    public String dashboard() {
        return "dashboard";
    }

    // ═══════ Admin ═══════
    @GetMapping("/admin/invite-codes")
    public String adminInviteCodes() {
        return "admin/invite-codes";
    }

    @GetMapping("/admin/subjects")
    public String adminSubjects() {
        return "admin/subjects";
    }

    // ═══════ Teacher ═══════
    @GetMapping("/teacher/classes")
    public String teacherClasses() {
        return "teacher/my-classes";
    }

    @GetMapping("/teacher/classes/{id}")
    public String teacherClassDetail() {
        return "teacher/class-detail";
    }

    @GetMapping("/teacher/classes/{id}/add-students")
    public String teacherAddStudents() {
        return "teacher/add-students";
    }

    @GetMapping("/teacher/classes/{id}/payments")
    public String teacherPayments() {
        return "teacher/payment-status";
    }

    // ═══════ Student ═══════
    @GetMapping("/student/classes")
    public String studentClasses() {
        return "student/my-classes";
    }

    @GetMapping("/student/join-class")
    public String studentJoinClass() {
        return "student/join-class";
    }
}
