package com.example.backend.controller;

import com.example.backend.model.LoginRequest;
import com.example.backend.model.LoginResponse;
import com.example.backend.model.User;
import com.example.backend.service.UserService;
import com.example.backend.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //验证token
    @GetMapping("/verify-token")
    public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(400).body("Authorization header is missing or malformed");
        }

        String token = authorizationHeader.substring(7);  // 去掉 "Bearer " 前缀

        if (JwtUtil.validateToken(token)) {
            Long userId = JwtUtil.extractUserId(token); // 提取用户ID
            User user = userService.getUserById(userId); // 获取用户信息
            // 返回包含用户信息和JWT Token的LoginResponse
            return ResponseEntity.ok(new LoginResponse(user, token));
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    //登录方法
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        logger.info("Received login request: userEmail={}, userPhoneNumber={}",
                loginRequest.getUserEmail(), loginRequest.getUserPhoneNumber());

        // 用户身份验证
        User user = userService.login(loginRequest.getUserEmail(), loginRequest.getUserPhoneNumber(), loginRequest.getPassword());

        if (user != null) {
            // 登录成功，生成JWT
            String token = JwtUtil.generateToken(user.getId());
            logger.info("Login successful for user: {}",
                    loginRequest.getUserEmail() != null ? loginRequest.getUserEmail() : loginRequest.getUserPhoneNumber());

            // 返回用户信息和JWT Token
            return ResponseEntity.ok(new LoginResponse(user, token));  // 假设LoginResponse是一个包含用户和token的返回类
        } else {
            logger.error("Login failed for userEmail: {} or userPhoneNumber: {}",
                    loginRequest.getUserEmail(), loginRequest.getUserPhoneNumber());
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
