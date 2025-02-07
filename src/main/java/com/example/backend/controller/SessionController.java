package com.example.backend.controller;

import com.example.backend.model.Session;
import com.example.backend.service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/sessions")
public class SessionController {

    private static final Logger logger = LoggerFactory.getLogger(SessionController.class);

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    // 获取会话列表
    @GetMapping("/get-sessions")
    public ResponseEntity<?> getSessions(@RequestParam("user_id") Long userId,
                                         @RequestParam(value = "limit", defaultValue = "20") int limit) {
        logger.info("Fetching sessions for user ID: {}, limit: {}", userId, limit);

        try {
            List<Session> sessions = sessionService.getSessionsByUserId(userId, limit);
            return ResponseEntity.ok(sessions);  // 返回会话列表
        } catch (Exception e) {
            logger.error("Error fetching sessions for user ID: {}", userId, e);
            return ResponseEntity.status(500).body("Error fetching sessions");
        }
    }
}
