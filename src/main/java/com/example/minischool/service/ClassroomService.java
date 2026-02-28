package com.example.minischool.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.minischool.entity.Classroom;
import com.example.minischool.entity.Enrollment;
import com.example.minischool.entity.Student;
import com.example.minischool.entity.Subject;
import com.example.minischool.entity.Teacher;
import com.example.minischool.entity.TuitionRecord;
import com.example.minischool.entity.User;
import com.example.minischool.enums.ClassroomStatus;
import com.example.minischool.enums.EnrollmentStatus;
import com.example.minischool.enums.TuitionStatus;
import com.example.minischool.repository.ClassroomRepository;
import com.example.minischool.repository.EnrollmentRepository;
import com.example.minischool.repository.SubjectRepository;
import com.example.minischool.repository.TeacherRepository;
import com.example.minischool.repository.TuitionRecordRepository;
import com.example.minischool.request.CreateClassroomRequest;
import com.example.minischool.util.ClassCodeGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final TuitionRecordRepository tuitionRecordRepository;

    /**
     * GV tạo lớp mới → auto-gen class code
     */
    @Transactional
    public Classroom createClassroom(CreateClassroomRequest request, User currentUser) {
        Teacher teacher = teacherRepository.findByUser(currentUser)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin giáo viên"));

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Môn học không tồn tại"));

        // Generate unique class code
        String classCode;
        do {
            classCode = ClassCodeGenerator.generate(subject.getCode());
        } while (classroomRepository.existsByClassCode(classCode));

        Classroom classroom = Classroom.builder()
                .name(request.getName())
                .classCode(classCode)
                .teacher(teacher)
                .subject(subject)
                .maxStudents(request.getMaxStudents() != null ? request.getMaxStudents() : 30)
                .schedule(request.getSchedule())
                .tuitionFee(request.getTuitionFee() != null ? request.getTuitionFee() : BigDecimal.ZERO)
                .status(ClassroomStatus.ACTIVE)
                .build();

        return classroomRepository.save(classroom);
    }

    /**
     * GV sửa thông tin lớp
     */
    @Transactional
    public void updateClassroom(Long classroomId, CreateClassroomRequest request, User currentUser) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Lớp không tồn tại"));

        Teacher teacher = teacherRepository.findByUser(currentUser)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin giáo viên"));

        if (!classroom.getTeacher().getId().equals(teacher.getId())) {
            throw new RuntimeException("Bạn không có quyền sửa lớp này");
        }

        if (request.getName() != null) classroom.setName(request.getName());
        if (request.getSchedule() != null) classroom.setSchedule(request.getSchedule());
        if (request.getMaxStudents() != null) classroom.setMaxStudents(request.getMaxStudents());
        if (request.getTuitionFee() != null) classroom.setTuitionFee(request.getTuitionFee());

        classroomRepository.save(classroom);
    }

    /**
     * Lấy danh sách lớp của GV
     */
    public List<Map<String, Object>> getMyClassrooms(User currentUser) {
        Teacher teacher = teacherRepository.findByUser(currentUser)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin giáo viên"));

        return classroomRepository.findByTeacher(teacher).stream()
                .map(this::toClassroomMap)
                .collect(Collectors.toList());
    }

    /**
     * Chi tiết lớp (kèm DS HS)
     */
    public Map<String, Object> getClassroomDetail(Long classroomId, User currentUser) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Lớp không tồn tại"));

        // Check ownership
        Teacher teacher = teacherRepository.findByUser(currentUser).orElse(null);
        if (teacher == null || !classroom.getTeacher().getId().equals(teacher.getId())) {
            throw new RuntimeException("Bạn không có quyền xem lớp này");
        }

        Map<String, Object> result = toClassroomMap(classroom);

        // DS học sinh
        List<Enrollment> enrollments = enrollmentRepository.findByClassroomAndStatus(classroom, EnrollmentStatus.ACTIVE);
        List<Map<String, Object>> students = enrollments.stream().map(e -> {
            Student s = e.getStudent();
            Map<String, Object> map = new HashMap<>();
            map.put("enrollmentId", e.getId());
            map.put("studentId", s.getId());
            map.put("studentCode", s.getStudentCode());
            map.put("fullName", s.getUser().getFullName());
            map.put("phone", s.getUser().getPhone());
            map.put("parentName", s.getParentName());
            map.put("parentPhone", s.getParentPhone());
            map.put("enrolledAt", e.getEnrolledAt());
            return map;
        }).collect(Collectors.toList());

        result.put("students", students);
        return result;
    }

    /**
     * Enroll student + auto tạo 12 tuition records kể từ tháng hiện tại
     */
    @Transactional
    public Enrollment enrollStudent(Student student, Classroom classroom) {
        if (enrollmentRepository.existsByStudentAndClassroom(student, classroom)) {
            throw new RuntimeException("Học sinh đã tham gia lớp này rồi");
        }

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .classroom(classroom)
                .status(EnrollmentStatus.ACTIVE)
                .build();
        enrollment = enrollmentRepository.save(enrollment);

        // Auto-create 12 tuition records starting from current month
        java.time.LocalDate now = java.time.LocalDate.now();
        for (int i = 0; i < 12; i++) {
            java.time.LocalDate target = now.plusMonths(i);
            int monthKey = target.getYear() * 100 + target.getMonthValue(); // e.g. 202503
            String label = "T" + target.getMonthValue() + "/" + target.getYear(); // e.g. T3/2025

            TuitionRecord record = TuitionRecord.builder()
                    .enrollment(enrollment)
                    .month(monthKey)
                    .monthLabel(label)
                    .amount(classroom.getTuitionFee() != null ? classroom.getTuitionFee() : BigDecimal.ZERO)
                    .status(TuitionStatus.UNPAID)
                    .build();
            tuitionRecordRepository.save(record);
        }

        return enrollment;
    }

    /**
     * Xóa HS khỏi lớp (soft: DROPPED)
     */
    @Transactional
    public void removeStudent(Long classroomId, Long studentId, User currentUser) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Lớp không tồn tại"));

        Teacher teacher = teacherRepository.findByUser(currentUser).orElse(null);
        if (teacher == null || !classroom.getTeacher().getId().equals(teacher.getId())) {
            throw new RuntimeException("Bạn không có quyền xóa HS khỏi lớp này");
        }

        Enrollment enrollment = enrollmentRepository.findByClassroom(classroom).stream()
                .filter(e -> e.getStudent().getId().equals(studentId) && e.getStatus() == EnrollmentStatus.ACTIVE)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Không tìm thấy HS trong lớp"));

        enrollment.setStatus(EnrollmentStatus.DROPPED);
        enrollmentRepository.save(enrollment);
    }

    private Map<String, Object> toClassroomMap(Classroom c) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", c.getId());
        map.put("name", c.getName());
        map.put("classCode", c.getClassCode());
        map.put("subjectName", c.getSubject().getName());
        map.put("maxStudents", c.getMaxStudents());
        map.put("schedule", c.getSchedule());
        map.put("tuitionFee", c.getTuitionFee());
        map.put("status", c.getStatus().name());
        map.put("createdAt", c.getCreatedAt());

        long activeCount = enrollmentRepository.countByClassroomAndStatus(c, EnrollmentStatus.ACTIVE);
        map.put("studentCount", activeCount);
        return map;
    }
}
