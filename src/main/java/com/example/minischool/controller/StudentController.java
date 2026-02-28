package com.example.minischool.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.minischool.dto.ApiResponse;
import com.example.minischool.entity.Classroom;
import com.example.minischool.entity.Enrollment;
import com.example.minischool.entity.Student;
import com.example.minischool.entity.User;
import com.example.minischool.enums.EnrollmentStatus;
import com.example.minischool.repository.ClassroomRepository;
import com.example.minischool.repository.EnrollmentRepository;
import com.example.minischool.repository.StudentRepository;
import com.example.minischool.repository.UserRepository;
import com.example.minischool.service.ClassroomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ClassroomRepository classroomRepository;
    private final ClassroomService classroomService;

    private User getUser(Authentication auth) {
        return userRepository.findByPhone(auth.getName())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
    }

    /**
     * GET /api/student/classes
     * DS lớp đang học
     */
    @GetMapping("/classes")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> myClasses(Authentication auth) {
        User user = getUser(auth);
        Student student = studentRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin HS"));

        List<Enrollment> enrollments = enrollmentRepository.findByStudent(student).stream()
                .filter(e -> e.getStatus() == EnrollmentStatus.ACTIVE)
                .collect(Collectors.toList());

        List<Map<String, Object>> classes = enrollments.stream().map(e -> {
            Classroom c = e.getClassroom();
            Map<String, Object> map = new HashMap<>();
            map.put("enrollmentId", e.getId());
            map.put("classroomId", c.getId());
            map.put("className", c.getName());
            map.put("classCode", c.getClassCode());
            map.put("subjectName", c.getSubject().getName());
            map.put("teacherName", c.getTeacher().getUser().getFullName());
            map.put("schedule", c.getSchedule());
            map.put("enrolledAt", e.getEnrolledAt());
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.ok("OK", classes));
    }

    /**
     * POST /api/student/join-class
     * HS tham gia lớp bằng mã lớp
     */
    @PostMapping("/join-class")
    public ResponseEntity<ApiResponse<Void>> joinClass(
            @RequestBody Map<String, String> body,
            Authentication auth) {
        String classCode = body.get("classCode");
        if (classCode == null || classCode.isBlank()) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Vui lòng nhập mã lớp"));
        }

        User user = getUser(auth);
        Student student = studentRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin HS"));

        Classroom classroom = classroomRepository.findByClassCode(classCode.trim().toUpperCase())
                .orElseThrow(() -> new RuntimeException("Mã lớp không hợp lệ: " + classCode));

        classroomService.enrollStudent(student, classroom);
        return ResponseEntity.ok(ApiResponse.ok("Tham gia lớp thành công: " + classroom.getName()));
    }

    /**
     * GET /api/student/info
     * Thông tin cá nhân HS
     */
    @GetMapping("/info")
    public ResponseEntity<ApiResponse<Map<String, Object>>> myInfo(Authentication auth) {
        User user = getUser(auth);
        Student student = studentRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin HS"));

        Map<String, Object> info = new HashMap<>();
        info.put("studentCode", student.getStudentCode());
        info.put("fullName", user.getFullName());
        info.put("phone", user.getPhone());
        info.put("parentName", student.getParentName());
        info.put("parentPhone", student.getParentPhone());

        long classCount = enrollmentRepository.findByStudent(student).stream()
                .filter(e -> e.getStatus() == EnrollmentStatus.ACTIVE).count();
        info.put("classCount", classCount);

        return ResponseEntity.ok(ApiResponse.ok("OK", info));
    }
}
