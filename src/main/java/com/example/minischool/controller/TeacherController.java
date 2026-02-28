package com.example.minischool.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.minischool.dto.ApiResponse;
import com.example.minischool.entity.Classroom;
import com.example.minischool.entity.User;
import com.example.minischool.repository.UserRepository;
import com.example.minischool.request.AddStudentsRequest;
import com.example.minischool.request.CreateClassroomRequest;
import com.example.minischool.service.ClassroomService;
import com.example.minischool.service.StudentService;
import com.example.minischool.service.TuitionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final ClassroomService classroomService;
    private final StudentService studentService;
    private final TuitionService tuitionService;
    private final UserRepository userRepository;

    private User getUser(Authentication auth) {
        return userRepository.findByPhone(auth.getName())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
    }

    // ═══════════════════════════════════════
    // CLASSROOM MANAGEMENT
    // ═══════════════════════════════════════

    @GetMapping("/classes")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> myClasses(Authentication auth) {
        List<Map<String, Object>> classes = classroomService.getMyClassrooms(getUser(auth));
        return ResponseEntity.ok(ApiResponse.ok("OK", classes));
    }

    @PostMapping("/classes")
    public ResponseEntity<ApiResponse<Map<String, Object>>> createClass(
            @RequestBody CreateClassroomRequest request, Authentication auth) {
        Classroom classroom = classroomService.createClassroom(request, getUser(auth));
        Map<String, Object> result = new HashMap<>();
        result.put("id", classroom.getId());
        result.put("name", classroom.getName());
        result.put("classCode", classroom.getClassCode());
        return ResponseEntity.ok(ApiResponse.ok("Tạo lớp thành công", result));
    }

    @PutMapping("/classes/{id}")
    public ResponseEntity<ApiResponse<Void>> editClass(
            @PathVariable Long id,
            @RequestBody CreateClassroomRequest request,
            Authentication auth) {
        classroomService.updateClassroom(id, request, getUser(auth));
        return ResponseEntity.ok(ApiResponse.ok("Cập nhật lớp thành công"));
    }

    @GetMapping("/classes/{id}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> classDetail(
            @PathVariable Long id, Authentication auth) {
        Map<String, Object> detail = classroomService.getClassroomDetail(id, getUser(auth));
        return ResponseEntity.ok(ApiResponse.ok("OK", detail));
    }

    // ═══════════════════════════════════════
    // STUDENT MANAGEMENT
    // ═══════════════════════════════════════

    @PostMapping("/classes/{id}/add-students")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> addStudents(
            @PathVariable Long id,
            @RequestBody AddStudentsRequest request,
            Authentication auth) {
        List<Map<String, Object>> accounts = studentService.addStudentsToClass(id, request, getUser(auth));
        return ResponseEntity.ok(ApiResponse.ok("Thêm học sinh thành công", accounts));
    }

    @PostMapping("/classes/{classId}/remove/{studentId}")
    public ResponseEntity<ApiResponse<Void>> removeStudent(
            @PathVariable Long classId, @PathVariable Long studentId, Authentication auth) {
        classroomService.removeStudent(classId, studentId, getUser(auth));
        return ResponseEntity.ok(ApiResponse.ok("Đã xóa HS khỏi lớp"));
    }

    @PutMapping("/classes/{classId}/students/{studentId}")
    public ResponseEntity<ApiResponse<Void>> updateStudent(
            @PathVariable Long classId,
            @PathVariable Long studentId,
            @RequestBody Map<String, String> body,
            Authentication auth) {
        studentService.updateStudentInfo(classId, studentId, body, getUser(auth));
        return ResponseEntity.ok(ApiResponse.ok("Cập nhật học sinh thành công"));
    }

    // ═══════════════════════════════════════
    // TUITION (HỌC PHÍ)
    // ═══════════════════════════════════════

    @GetMapping("/classes/{id}/payments")
    public ResponseEntity<ApiResponse<Map<String, Object>>> paymentStatus(
            @PathVariable Long id, Authentication auth) {
        Map<String, Object> data = tuitionService.getPaymentStatus(id, getUser(auth));
        return ResponseEntity.ok(ApiResponse.ok("OK", data));
    }

    @PostMapping("/classes/{id}/payments/confirm")
    public ResponseEntity<ApiResponse<Void>> confirmPayment(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body,
            Authentication auth) {
        Long enrollmentId = Long.valueOf(body.get("enrollmentId").toString());
        @SuppressWarnings("unchecked")
        List<Integer> months = (List<Integer>) body.get("months");
        tuitionService.confirmPayment(id, enrollmentId, months, getUser(auth));
        return ResponseEntity.ok(ApiResponse.ok("Xác nhận thanh toán thành công"));
    }

    @PostMapping("/classes/{id}/payments/undo")
    public ResponseEntity<ApiResponse<Void>> undoPayment(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body,
            Authentication auth) {
        Long enrollmentId = Long.valueOf(body.get("enrollmentId").toString());
        @SuppressWarnings("unchecked")
        List<Integer> months = (List<Integer>) body.get("months");
        tuitionService.undoPayment(id, enrollmentId, months, getUser(auth));
        return ResponseEntity.ok(ApiResponse.ok("Đã hủy xác nhận"));
    }
}
