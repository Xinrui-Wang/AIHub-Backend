package com.example.backend.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordValidationExample {
    public static void main(String[] args) {
        // 假设从数据库中查询到的加密密码
        String storedHashedPassword = "$2a$10$IQJbDqyKKoaGkqeT95lYYOmbS3PXuQsDjqDMuhHjwNHRH9WsAVMqK";

        // 用户输入的密码
        String inputPassword = "yourPassword123";

        // 创建 BCryptPasswordEncoder 实例
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // 验证用户输入的密码是否与数据库存储的密码匹配
        boolean isPasswordMatch = passwordEncoder.matches(inputPassword, storedHashedPassword);

        // 输出结果
        System.out.println("Password matches: " + isPasswordMatch);
    }
}
