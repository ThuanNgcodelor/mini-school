package com.example.minischool.service;

import com.example.minischool.entity.*;
import com.example.minischool.enums.RoleName;
import com.example.minischool.repository.*;
import com.example.minischool.request.LoginRequest;
import com.example.minischool.request.RegisterStudentRequest;
import com.example.minischool.request.RegisterTeacherRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final ClassroomRepository classroomRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final InviteCodeService inviteCodeService;
    private final AuthenticationManager authenticationManager;

    /**
     * Đăng nhập → trả JWT token
     */
    public String login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getPhone(), request.getPassword())
        );

        User user = userRepository.findByPhone(request.getPhone())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());
        claims.put("name", user.getFullName());
        claims.put("userId", user.getId());

        return jwtService.generateToken(claims, user.getPhone());
    }

    /**
     * Đăng ký Giáo viên (cần invite code)
     */
    @Transactional
    public User registerTeacher(RegisterTeacherRequest request) {
        // Check trùng SĐT
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Số điện thoại đã được sử dụng");
        }

        // Tạo User
        User user = User.builder()
                .phone(request.getPhone())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .role(RoleName.TEACHER)
                .active(true)
                .build();
        user = userRepository.save(user);

        // Validate + đánh dấu invite code đã dùng
        inviteCodeService.validateAndUse(request.getInviteCode(), user);

        // Tạo Teacher profile
        Teacher teacher = Teacher.builder()
                .user(user)
                .specialization(request.getSpecialization())
                .build();
        teacherRepository.save(teacher);

        return user;
    }

    /**
     * Đăng ký Học sinh (cần mã lớp)
     */
    @Transactional
    public User registerStudent(RegisterStudentRequest request) {
        // Kiểm tra lớp tồn tại
        Classroom classroom = classroomRepository.findByClassCode(request.getClassCode())
                .orElseThrow(() -> new RuntimeException("Mã lớp không hợp lệ: " + request.getClassCode()));

        User user;
        Student student;

        // Check SĐT đã tồn tại chưa (dedup)
        if (userRepository.existsByPhone(request.getPhone())) {
            user = userRepository.findByPhone(request.getPhone())
                    .orElseThrow(() -> new RuntimeException("Lỗi hệ thống"));

            if (user.getRole() != RoleName.STUDENT) {
                throw new RuntimeException("Số điện thoại này đã được dùng cho tài khoản khác");
            }

            student = studentRepository.findByUser(user)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin học sinh"));
        } else {
            // Tạo User mới
            user = User.builder()
                    .phone(request.getPhone())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .fullName(request.getFullName())
                    .role(RoleName.STUDENT)
                    .active(true)
                    .build();
            user = userRepository.save(user);

            // Tạo Student profile
            String studentCode = String.format("HS-%05d", user.getId());
            student = Student.builder()
                    .user(user)
                    .studentCode(studentCode)
                    .parentName(request.getParentName())
                    .parentPhone(request.getParentPhone())
                    .build();
            student = studentRepository.save(student);
        }

        // Kiểm tra đã enroll chưa
        if (enrollmentRepository.existsByStudentAndClassroom(student, classroom)) {
            throw new RuntimeException("Học sinh đã tham gia lớp này rồi");
        }

        // Enroll vào lớp
        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .classroom(classroom)
                .build();
        enrollmentRepository.save(enrollment);

        return user;
    }

    /**
     * Lấy thông tin user hiện tại
     */
    public Map<String, Object> getCurrentUserInfo(String phone) {
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));

        Map<String, Object> info = new HashMap<>();
        info.put("id", user.getId());
        info.put("phone", user.getPhone());
        info.put("email", user.getEmail());
        info.put("fullName", user.getFullName());
        info.put("role", user.getRole().name());
        info.put("active", user.getActive());
        return info;
    }
}
