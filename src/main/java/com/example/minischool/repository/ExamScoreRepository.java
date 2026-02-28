package com.example.minischool.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.minischool.entity.Exam;
import com.example.minischool.entity.ExamScore;
import com.example.minischool.entity.Student;

@Repository
public interface ExamScoreRepository extends JpaRepository<ExamScore, Long> {

    List<ExamScore> findByExam(Exam exam);

    List<ExamScore> findByExamId(Long examId);

    List<ExamScore> findByStudent(Student student);

    List<ExamScore> findByStudentId(Long studentId);

    Optional<ExamScore> findByExamAndStudent(Exam exam, Student student);
}
