package com.example.minischool.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CreateClassroomRequest {
    private String name;
    private Long subjectId;
    private Integer maxStudents;
    private String schedule;
    private BigDecimal tuitionFee;
}
