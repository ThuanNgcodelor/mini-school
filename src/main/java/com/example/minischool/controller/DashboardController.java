package com.example.minischool.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.minischool.dto.ApiResponse;
import com.example.minischool.entity.User;
import com.example.minischool.repository.SubjectRepository;
import com.example.minischool.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DashboardController {

    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    /**
     * GET /api/dashboard
     * Trả thông tin dashboard theo role
     */
    @GetMapping("/api/dashboard")
    public ResponseEntity<ApiResponse<Map<String, Object>>> dashboard(Authentication authentication) {
        User user = userRepository.findByPhone(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        Map<String, Object> data = new HashMap<>();
        data.put("role", user.getRole().name());
        data.put("fullName", user.getFullName());
        data.put("message", "Chào mừng " + user.getFullName() + "!");

        return ResponseEntity.ok(ApiResponse.ok("OK", data));
    }

    /**
     * GET /api/subjects
     * DS môn học (tất cả user đều xem được)
     */
    @GetMapping("/api/subjects")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> subjects() {
        List<Map<String, Object>> subjects = subjectRepository.findAll().stream().map(s -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", s.getId());
            map.put("name", s.getName());
            map.put("code", s.getCode());
            return map;
        }).collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(ApiResponse.ok("OK", subjects));
    }
}
