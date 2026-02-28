package com.example.minischool.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.minischool.entity.Classroom;
import com.example.minischool.entity.Enrollment;
import com.example.minischool.entity.Student;
import com.example.minischool.entity.Teacher;
import com.example.minischool.entity.TuitionRecord;
import com.example.minischool.entity.User;
import com.example.minischool.enums.EnrollmentStatus;
import com.example.minischool.enums.TuitionStatus;
import com.example.minischool.repository.ClassroomRepository;
import com.example.minischool.repository.EnrollmentRepository;
import com.example.minischool.repository.TeacherRepository;
import com.example.minischool.repository.TuitionRecordRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TuitionService {

    private final ClassroomRepository classroomRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final TuitionRecordRepository tuitionRecordRepository;
    private final TeacherRepository teacherRepository;

    /**
     * Bảng học phí: mỗi HS → danh sách tháng → PAID / UNPAID
     */
    public Map<String, Object> getPaymentStatus(Long classroomId, User currentUser) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Lớp không tồn tại"));

        Teacher teacher = teacherRepository.findByUser(currentUser).orElse(null);
        if (teacher == null || !classroom.getTeacher().getId().equals(teacher.getId())) {
            throw new RuntimeException("Bạn không có quyền xem lớp này");
        }

        Map<String, Object> result = new HashMap<>();
        result.put("classroomName", classroom.getName());
        result.put("tuitionFee", classroom.getTuitionFee());

        List<Enrollment> enrollments = enrollmentRepository.findByClassroomAndStatus(classroom, EnrollmentStatus.ACTIVE);

        long totalPaid = 0;
        long totalUnpaid = 0;

        // Collect all unique month keys for dynamic columns
        Set<Integer> allMonthKeys = new TreeSet<>();

        List<Map<String, Object>> studentRows = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            Student student = enrollment.getStudent();
            Map<String, Object> row = new HashMap<>();
            row.put("studentId", student.getId());
            row.put("enrollmentId", enrollment.getId());
            row.put("fullName", student.getUser().getFullName());
            row.put("studentCode", student.getStudentCode());

            List<TuitionRecord> records = tuitionRecordRepository.findByEnrollment(enrollment);
            records.sort(Comparator.comparingInt(TuitionRecord::getMonth));

            List<Map<String, Object>> months = new ArrayList<>();
            for (TuitionRecord r : records) {
                allMonthKeys.add(r.getMonth());
                Map<String, Object> m = new HashMap<>();
                m.put("id", r.getId());
                m.put("month", r.getMonth());
                m.put("monthLabel", r.getMonthLabel());
                m.put("amount", r.getAmount());
                m.put("status", r.getStatus().name()); // PAID or UNPAID
                m.put("paidAt", r.getPaidAt());

                long amt = r.getAmount() != null ? r.getAmount().longValue() : 0;
                if (r.getStatus() == TuitionStatus.PAID) {
                    totalPaid += amt;
                } else {
                    totalUnpaid += amt;
                }
                months.add(m);
            }
            row.put("months", months);
            studentRows.add(row);
        }

        // Build column headers
        List<Map<String, Object>> columns = allMonthKeys.stream().map(key -> {
            int year = key / 100;
            int month = key % 100;
            Map<String, Object> col = new HashMap<>();
            col.put("key", key);
            col.put("label", "T" + month + "/" + year);
            return col;
        }).collect(Collectors.toList());

        result.put("columns", columns);
        result.put("students", studentRows);
        result.put("totalPaid", totalPaid);
        result.put("totalUnpaid", totalUnpaid);
        return result;
    }

    @Transactional
    public void confirmPayment(Long classroomId, Long enrollmentId, List<Integer> months, User currentUser) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Lớp không tồn tại"));
        Teacher teacher = teacherRepository.findByUser(currentUser).orElse(null);
        if (teacher == null || !classroom.getTeacher().getId().equals(teacher.getId())) {
            throw new RuntimeException("Bạn không có quyền");
        }
        List<TuitionRecord> records = tuitionRecordRepository.findByEnrollmentIdAndMonthIn(enrollmentId, months);
        for (TuitionRecord record : records) {
            if (record.getStatus() == TuitionStatus.UNPAID) {
                record.setStatus(TuitionStatus.PAID);
                record.setPaidAt(LocalDateTime.now());
                record.setConfirmedBy(currentUser);
                tuitionRecordRepository.save(record);
            }
        }
    }

    @Transactional
    public void undoPayment(Long classroomId, Long enrollmentId, List<Integer> months, User currentUser) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Lớp không tồn tại"));
        Teacher teacher = teacherRepository.findByUser(currentUser).orElse(null);
        if (teacher == null || !classroom.getTeacher().getId().equals(teacher.getId())) {
            throw new RuntimeException("Bạn không có quyền");
        }
        List<TuitionRecord> records = tuitionRecordRepository.findByEnrollmentIdAndMonthIn(enrollmentId, months);
        for (TuitionRecord record : records) {
            if (record.getStatus() == TuitionStatus.PAID) {
                record.setStatus(TuitionStatus.UNPAID);
                record.setPaidAt(null);
                record.setConfirmedBy(null);
                tuitionRecordRepository.save(record);
            }
        }
    }
}
