package com.example.minischool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.minischool.entity.Classroom;
import com.example.minischool.entity.Exam;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    List<Exam> findByClassroom(Classroom classroom);

    List<Exam> findByClassroomId(Long classroomId);
}
