package com.example.minischool.request;

import java.util.List;

import lombok.Data;

@Data
public class AddStudentsRequest {
    /**
     * Danh sách HS dạng [ { "fullName": "...", "phone": "..." }, ... ]
     */
    private List<StudentEntry> students;

    @Data
    public static class StudentEntry {
        private String fullName;
        private String phone;
        private String parentName;
        private String parentPhone;
    }
}
