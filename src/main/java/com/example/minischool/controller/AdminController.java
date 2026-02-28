package com.example.minischool.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.minischool.dto.ApiResponse;
import com.example.minischool.entity.InviteCode;
import com.example.minischool.entity.Subject;
import com.example.minischool.entity.User;
import com.example.minischool.enums.RoleName;
import com.example.minischool.repository.ClassroomRepository;
import com.example.minischool.repository.UserRepository;
import com.example.minischool.service.InviteCodeService;
import com.example.minischool.service.SubjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final InviteCodeService inviteCodeService;
    private final SubjectService subjectService;
    private final UserRepository userRepository;
    private final ClassroomRepository classroomRepository;

    // ═══════════════════════════════════════
    // DASHBOARD
    // ═══════════════════════════════════════

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<Map<String, Object>>> dashboard() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userRepository.count());
        stats.put("totalTeachers", userRepository.findAll().stream()
                .filter(u -> u.getRole() == RoleName.TEACHER).count());
        stats.put("totalStudents", userRepository.findAll().stream()
                .filter(u -> u.getRole() == RoleName.STUDENT).count());
        stats.put("totalClassrooms", classroomRepository.count());
        return ResponseEntity.ok(ApiResponse.ok("OK", stats));
    }

    // ═══════════════════════════════════════
    // INVITE CODES
    // ═══════════════════════════════════════

    @PostMapping("/invite-codes")
    public ResponseEntity<ApiResponse<Map<String, Object>>> createInviteCode(
            @RequestBody(required = false) Map<String, Integer> body,
            Authentication authentication) {
        User admin = userRepository.findByPhone(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Admin không tồn tại"));

        int validDays = (body != null && body.containsKey("validDays")) ? body.get("validDays") : 30;
        InviteCode code = inviteCodeService.createInviteCode(admin, validDays);

        Map<String, Object> result = new HashMap<>();
        result.put("code", code.getCode());
        result.put("expiresAt", code.getExpiresAt());
        return ResponseEntity.ok(ApiResponse.ok("Tạo invite code thành công", result));
    }

    @GetMapping("/invite-codes")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getInviteCodes() {
        List<InviteCode> codes = inviteCodeService.getAllInviteCodes();
        List<Map<String, Object>> result = codes.stream().map(c -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", c.getId());
            map.put("code", c.getCode());
            map.put("expiresAt", c.getExpiresAt());
            map.put("active", c.getActive());
            map.put("used", c.getUsedBy() != null);
            map.put("usedAt", c.getUsedAt());
            return map;
        }).toList();
        return ResponseEntity.ok(ApiResponse.ok("OK", result));
    }

    // ═══════════════════════════════════════
    // SUBJECTS (CRUD)
    // ═══════════════════════════════════════

    @GetMapping("/subjects")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getSubjects() {
        return ResponseEntity.ok(ApiResponse.ok("OK", subjectService.getAllSubjects()));
    }

    @PostMapping("/subjects")
    public ResponseEntity<ApiResponse<Map<String, Object>>> createSubject(@RequestBody Map<String, String> body) {
        Subject subject = subjectService.createSubject(
                body.get("name"), body.get("code"), body.get("description"));
        Map<String, Object> result = new HashMap<>();
        result.put("id", subject.getId());
        result.put("name", subject.getName());
        result.put("code", subject.getCode());
        return ResponseEntity.ok(ApiResponse.ok("Tạo môn học thành công", result));
    }

    @PutMapping("/subjects/{id}")
    public ResponseEntity<ApiResponse<Void>> updateSubject(@PathVariable Long id, @RequestBody Map<String, String> body) {
        subjectService.updateSubject(id, body.get("name"), body.get("code"), body.get("description"));
        return ResponseEntity.ok(ApiResponse.ok("Cập nhật thành công"));
    }

    @DeleteMapping("/subjects/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.ok(ApiResponse.ok("Xóa thành công"));
    }

    // ═══════════════════════════════════════
    // VIEW LISTS (read-only)
    // ═══════════════════════════════════════

    @GetMapping("/teachers")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getTeachers() {
        List<Map<String, Object>> teachers = userRepository.findAll().stream()
                .filter(u -> u.getRole() == RoleName.TEACHER)
                .map(u -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", u.getId());
                    map.put("fullName", u.getFullName());
                    map.put("phone", u.getPhone());
                    map.put("email", u.getEmail());
                    map.put("active", u.getActive());
                    map.put("createdAt", u.getCreatedAt());
                    return map;
                }).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.ok("OK", teachers));
    }

    @GetMapping("/students")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getStudents() {
        List<Map<String, Object>> students = userRepository.findAll().stream()
                .filter(u -> u.getRole() == RoleName.STUDENT)
                .map(u -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", u.getId());
                    map.put("fullName", u.getFullName());
                    map.put("phone", u.getPhone());
                    map.put("active", u.getActive());
                    map.put("createdAt", u.getCreatedAt());
                    return map;
                }).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.ok("OK", students));
    }

    @GetMapping("/classrooms")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getClassrooms() {
        List<Map<String, Object>> classrooms = classroomRepository.findAll().stream()
                .map(c -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", c.getId());
                    map.put("name", c.getName());
                    map.put("classCode", c.getClassCode());
                    map.put("teacherName", c.getTeacher().getUser().getFullName());
                    map.put("subjectName", c.getSubject().getName());
                    map.put("status", c.getStatus().name());
                    map.put("maxStudents", c.getMaxStudents());
                    return map;
                }).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.ok("OK", classrooms));
    }
}
