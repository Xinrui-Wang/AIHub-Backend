package com.example.backend.controller;

import com.example.backend.model.Message;
import com.example.backend.model.Session;
import com.example.backend.service.SessionService;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/sessions")
public class SessionController {

    private static final Logger logger = LoggerFactory.getLogger(SessionController.class);

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }


    // 删除会话接口（新增）
    @DeleteMapping("/{session_id}/delete")
    public ResponseEntity<?> deleteSession(
            @PathVariable("session_id") UUID sessionId,
            @RequestParam("user_id") Long userId) {

        logger.info("Deleting session: userId={}, sessionId={}", userId, sessionId);

        try {
            boolean isDeleted = sessionService.deleteSession(userId, sessionId);
            if (isDeleted) {
                return ResponseEntity.ok().build();
            } else {
                logger.warn("Session not found or permission denied");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error deleting session: {}", sessionId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Failed to delete session"));
        }
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

    // 获取指定 session_id 的所有消息
    @GetMapping("/{session_id}/messages")
    public ResponseEntity<?> getMessagesBySessionId(@PathVariable UUID session_id) {
        logger.info("Fetching messages for session_id: {}", session_id);
        try {
            List<Message> messages = sessionService.getMessagesBySessionId(session_id);
            return ResponseEntity.ok(messages);  // 返回消息列表
        } catch (Exception e) {
            logger.error("Error fetching messages for session_id: {}", session_id, e);
            return ResponseEntity.status(500).body("Error fetching messages");
        }
    }

    // 保存用户和系统消息
    @PostMapping("/{session_id}/messages")
    public ResponseEntity<?> saveMessage(
            @PathVariable UUID session_id,
            @RequestBody Map<String, String> messageData) {  // 接收前端传过来的消息数据
        String sender = messageData.get("sender");  // 获取发送者
        String messageContent = messageData.get("message");  // 获取消息内容

        logger.info("Saving message for session_id: {}, sender: {}, message: {}",
                session_id, sender, messageContent);

        try {
            // 创建消息对象并设置相关信息
            Message message = new Message();
            message.setSessionId(session_id);
            message.setSender(sender);
            message.setMessageContent(messageContent);

            // 保存消息到数据库
            sessionService.saveMessage(message);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error saving message for session_id: {}", session_id, e);
            return ResponseEntity.status(500).body("Error saving message");
        }
    }

    //插入新的会话
    // 插入新的会话
    @PostMapping("/{session_id}/insert-session")
    public ResponseEntity<?> insertSession(@PathVariable UUID session_id, @RequestBody Map<String, Object> sessionData) {
        Long userId = Long.valueOf(sessionData.get("userId").toString());  // 获取用户 ID
        String sessionName = sessionData.get("sessionName").toString();  // 获取会话名称

        logger.info("Inserting new session: userId: {}, sessionId: {}, sessionName: {}", userId, session_id, sessionName);

        try {
            // 调用 service 层的方法插入会话数据
            boolean isCreated = sessionService.insertSession(userId, session_id, sessionName);

            if (isCreated) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(500).body("Error inserting session");
            }
        } catch (Exception e) {
            logger.error("Error inserting session: userId: {}, sessionId: {}", userId, session_id, e);
            return ResponseEntity.status(500).body("Error inserting session");
        }
    }

}
