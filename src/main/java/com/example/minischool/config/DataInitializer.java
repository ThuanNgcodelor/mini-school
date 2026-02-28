package com.example.minischool.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.minischool.entity.User;
import com.example.minischool.enums.RoleName;
import com.example.minischool.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Seed admin account nếu chưa có
        if (!userRepository.existsByPhone("admin")) {
            User admin = User.builder()
                    .phone("admin")
                    .email("admin@minischool.vn")
                    .password(passwordEncoder.encode("admin123"))
                    .fullName("Quản trị viên")
                    .role(RoleName.ADMIN)
                    .active(true)
                    .build();
            userRepository.save(admin);
            log.info("Đã tạo tài khoản Admin mặc định — phone: admin / password: admin123");
        }
    }
}
