package com.example.minischool.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.minischool.entity.Classroom;
import com.example.minischool.entity.Student;
import com.example.minischool.entity.Teacher;
import com.example.minischool.entity.User;
import com.example.minischool.enums.RoleName;
import com.example.minischool.repository.ClassroomRepository;
import com.example.minischool.repository.StudentRepository;
import com.example.minischool.repository.TeacherRepository;
import com.example.minischool.repository.UserRepository;
import com.example.minischool.request.AddStudentsRequest;
import com.example.minischool.util.PasswordGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final ClassroomRepository classroomRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClassroomService classroomService;

    /**
     * GV thêm hàng loạt HS vào lớp
     * Trả về danh sách accounts (gồm password raw để in)
     */
    @Transactional
    public List<Map<String, Object>> addStudentsToClass(Long classroomId, AddStudentsRequest request, User currentUser) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Lớp không tồn tại"));

        // Check quyền sở hữu
        Teacher teacher = teacherRepository.findByUser(currentUser)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin GV"));
        if (!classroom.getTeacher().getId().equals(teacher.getId())) {
            throw new RuntimeException("Bạn không có quyền thêm HS vào lớp này");
        }

        List<Map<String, Object>> results = new ArrayList<>();

        for (AddStudentsRequest.StudentEntry entry : request.getStudents()) {
            Map<String, Object> account = new HashMap<>();
            account.put("fullName", entry.getFullName());
            account.put("phone", entry.getPhone());

            Student student;
            String rawPassword = null;
            boolean isNew = false;

            // Check SĐT đã tồn tại?
            Optional<User> existingUser = userRepository.findByPhone(entry.getPhone());
            if (existingUser.isPresent()) {
                User user = existingUser.get();
                if (user.getRole() != RoleName.STUDENT) {
                    account.put("status", "SKIP");
                    account.put("message", "SĐT đã dùng cho tài khoản khác (không phải HS)");
                    results.add(account);
                    continue;
                }
                student = studentRepository.findByUser(user)
                        .orElseThrow(() -> new RuntimeException("Lỗi: User có role STUDENT nhưng không có Student profile"));
            } else {
                // Tạo account mới
                rawPassword = PasswordGenerator.generate();
                isNew = true;

                User newUser = User.builder()
                        .phone(entry.getPhone())
                        .password(passwordEncoder.encode(rawPassword))
                        .fullName(entry.getFullName())
                        .role(RoleName.STUDENT)
                        .active(true)
                        .build();
                newUser = userRepository.save(newUser);

                String studentCode = String.format("HS-%05d", newUser.getId());
                student = Student.builder()
                        .user(newUser)
                        .studentCode(studentCode)
                        .parentName(entry.getParentName())
                        .parentPhone(entry.getParentPhone())
                        .build();
                student = studentRepository.save(student);
            }

            // Enroll (skip nếu đã enroll rồi)
            try {
                classroomService.enrollStudent(student, classroom);
                account.put("status", isNew ? "CREATED" : "ENROLLED");
                account.put("studentCode", student.getStudentCode());
                account.put("parentName", student.getParentName());
                account.put("parentPhone", student.getParentPhone());
                if (rawPassword != null) {
                    account.put("password", rawPassword);
                }
                account.put("message", isNew ? "Tạo mới + gán vào lớp" : "Đã có account, gán vào lớp");
            } catch (RuntimeException e) {
                account.put("status", "SKIP");
                account.put("message", e.getMessage());
            }

            results.add(account);
        }

        return results;
    }

    /**
     * GV cập nhật thông tin HS trong lớp
     */
    @Transactional
    public void updateStudentInfo(Long classroomId, Long studentId, Map<String, String> body, User currentUser) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Lớp không tồn tại"));

        Teacher teacher = teacherRepository.findByUser(currentUser)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin giáo viên"));

        if (!classroom.getTeacher().getId().equals(teacher.getId())) {
            throw new RuntimeException("Bạn không có quyền sửa HS trong lớp này");
        }

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy học sinh"));

        User studentUser = student.getUser();

        if (body.containsKey("fullName") && body.get("fullName") != null && !body.get("fullName").isBlank()) {
            studentUser.setFullName(body.get("fullName").trim());
        }
        if (body.containsKey("phone") && body.get("phone") != null && !body.get("phone").isBlank()) {
            studentUser.setPhone(body.get("phone").trim());
        }
        if (body.containsKey("parentName")) {
            student.setParentName(body.get("parentName") != null ? body.get("parentName").trim() : null);
        }
        if (body.containsKey("parentPhone")) {
            student.setParentPhone(body.get("parentPhone") != null ? body.get("parentPhone").trim() : null);
        }

        userRepository.save(studentUser);
        studentRepository.save(student);
    }
}
