package com.example.minischool.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.minischool.entity.Subject;
import com.example.minischool.repository.SubjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public List<Map<String, Object>> getAllSubjects() {
        return subjectRepository.findAll().stream().map(s -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", s.getId());
            map.put("name", s.getName());
            map.put("code", s.getCode());
            map.put("description", s.getDescription());
            return map;
        }).collect(Collectors.toList());
    }

    public Subject createSubject(String name, String code, String description) {
        if (subjectRepository.findByCode(code).isPresent()) {
            throw new RuntimeException("Mã môn học đã tồn tại: " + code);
        }
        Subject subject = Subject.builder()
                .name(name)
                .code(code.toUpperCase())
                .description(description)
                .build();
        return subjectRepository.save(subject);
    }

    public Subject updateSubject(Long id, String name, String code, String description) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Môn học không tồn tại"));
        subject.setName(name);
        subject.setCode(code.toUpperCase());
        subject.setDescription(description);
        return subjectRepository.save(subject);
    }

    public void deleteSubject(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new RuntimeException("Môn học không tồn tại");
        }
        subjectRepository.deleteById(id);
    }
}
