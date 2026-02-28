package com.example.minischool.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.minischool.entity.Classroom;
import com.example.minischool.entity.Enrollment;
import com.example.minischool.entity.Student;
import com.example.minischool.enums.EnrollmentStatus;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByStudent(Student student);

    List<Enrollment> findByStudentId(Long studentId);

    List<Enrollment> findByClassroom(Classroom classroom);

    List<Enrollment> findByClassroomId(Long classroomId);

    Optional<Enrollment> findByStudentAndClassroom(Student student, Classroom classroom);

    boolean existsByStudentAndClassroom(Student student, Classroom classroom);

    List<Enrollment> findByClassroomAndStatus(Classroom classroom, EnrollmentStatus status);

    long countByClassroomAndStatus(Classroom classroom, EnrollmentStatus status);
}
