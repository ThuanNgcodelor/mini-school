package com.example.minischool.util;

import java.security.SecureRandom;

public class PasswordGenerator {

    private static final String CHARS = "abcdefghijkmnpqrstuvwxyz23456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Tạo mật khẩu random 6 ký tự (dễ đọc, bỏ l/o/1/0 tránh nhầm)
     */
    public static String generate() {
        return generate(6);
    }

    public static String generate(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
        }
        return sb.toString();
    }
}
