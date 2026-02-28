package com.example.minischool.service;

import com.example.minischool.entity.InviteCode;
import com.example.minischool.entity.User;
import com.example.minischool.repository.InviteCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InviteCodeService {

    private final InviteCodeRepository inviteCodeRepository;

    /**
     * Admin tạo invite code mới cho GV đăng ký
     */
    public InviteCode createInviteCode(User admin, int validDays) {
        String code = "TEACHER-" + LocalDateTime.now().getYear() + "-"
                + UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        InviteCode inviteCode = InviteCode.builder()
                .code(code)
                .createdBy(admin)
                .expiresAt(LocalDateTime.now().plusDays(validDays))
                .active(true)
                .build();

        return inviteCodeRepository.save(inviteCode);
    }

    /**
     * Validate invite code khi GV đăng ký
     */
    public InviteCode validateAndUse(String code, User teacher) {
        InviteCode inviteCode = inviteCodeRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Mã mời không hợp lệ"));

        if (!inviteCode.getActive()) {
            throw new RuntimeException("Mã mời đã bị vô hiệu hóa");
        }
        if (inviteCode.getUsedBy() != null) {
            throw new RuntimeException("Mã mời đã được sử dụng");
        }
        if (inviteCode.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Mã mời đã hết hạn");
        }

        inviteCode.setUsedBy(teacher);
        inviteCode.setUsedAt(LocalDateTime.now());
        return inviteCodeRepository.save(inviteCode);
    }

    public List<InviteCode> getAllInviteCodes() {
        return inviteCodeRepository.findAll();
    }

    public List<InviteCode> getAvailableCodes() {
        return inviteCodeRepository.findByUsedByIsNull();
    }
}
