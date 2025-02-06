package com.example.backend.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderExample {
    public static void main(String[] args) {
        // 创建 BCryptPasswordEncoder 实例
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // 假设你要加密的明文密码
        String rawPassword = "password123";

        // 使用 BCrypt 加密密码
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // 输出加密后的密码
        System.out.println("Encoded Password: " + encodedPassword);
    }
}
