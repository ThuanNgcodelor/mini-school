package com.example.minischool.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.minischool.entity.Classroom;
import com.example.minischool.entity.Teacher;
import com.example.minischool.enums.ClassroomStatus;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    List<Classroom> findByTeacher(Teacher teacher);

    List<Classroom> findByTeacherId(Long teacherId);

    Optional<Classroom> findByClassCode(String classCode);

    List<Classroom> findByStatus(ClassroomStatus status);

    boolean existsByClassCode(String classCode);
}
