package com.example.minischool.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.minischool.entity.Enrollment;
import com.example.minischool.entity.TuitionRecord;
import com.example.minischool.enums.TuitionStatus;

@Repository
public interface TuitionRecordRepository extends JpaRepository<TuitionRecord, Long> {

    List<TuitionRecord> findByEnrollment(Enrollment enrollment);

    List<TuitionRecord> findByEnrollmentId(Long enrollmentId);

    Optional<TuitionRecord> findByEnrollmentAndMonth(Enrollment enrollment, Integer month);

    List<TuitionRecord> findByEnrollmentAndStatus(Enrollment enrollment, TuitionStatus status);

    List<TuitionRecord> findByEnrollmentIdAndMonthIn(Long enrollmentId, List<Integer> months);
}
