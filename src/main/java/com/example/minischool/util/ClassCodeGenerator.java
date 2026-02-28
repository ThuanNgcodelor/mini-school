package com.example.minischool.util;

import java.security.SecureRandom;

public class ClassCodeGenerator {

    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Tạo mã lớp dạng: prefix-XXXXX (VD: T10A-X7K2M)
     */
    public static String generate(String prefix) {
        StringBuilder sb = new StringBuilder();
        if (prefix != null && !prefix.isBlank()) {
            // Clean prefix: max 4 chars, uppercase, no spaces
            sb.append(prefix.replaceAll("[^a-zA-Z0-9]", "").toUpperCase(), 0, Math.min(prefix.length(), 4));
            sb.append("-");
        }
        for (int i = 0; i < 5; i++) {
            sb.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
        }
        return sb.toString();
    }

    /**
     * Tạo mã lớp random không prefix
     */
    public static String generate() {
        return generate(null);
    }
}
