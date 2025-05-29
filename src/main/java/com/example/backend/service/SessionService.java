package com.example.backend.service;

import ch.qos.logback.classic.Logger;
import com.example.backend.mapper.SessionMapper;
import com.example.backend.mapper.MessageMapper;
import com.example.backend.model.Message;
import com.example.backend.model.Session;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Log
@Service
public class SessionService {

    private final SessionMapper sessionMapper;
    private final MessageMapper messageMapper;

    public SessionService(SessionMapper sessionMapper, MessageMapper messageMapper) {
        this.sessionMapper = sessionMapper;
        this.messageMapper = messageMapper;
    }

    /**
     * 删除指定用户的会话（包括关联消息）
     * @param userId 用户ID
     * @param sessionId 会话ID
     * @return 是否删除成功
     */
    @Transactional
    public boolean deleteSession(Long userId, UUID sessionId) {
        try {
            // 1. 验证会话所有权
            Session session = sessionMapper.findSessionById(sessionId.toString());
            if (session == null || !session.getUserId().equals(userId)) {
                log.warning("Session not found or permission denied. UserId: " + userId + ", SessionId: " + sessionId);
                return false;
            }

            // 2. 删除关联消息（先删从表）
            int deletedMessages = messageMapper.deleteBySessionId(sessionId);
            log.info("Deleted " + deletedMessages + " messages for session: " + sessionId);

            // 3. 删除会话（主表）
            int deletedSessions = sessionMapper.deleteById(sessionId.toString());
            if (deletedSessions == 0) {
                log.warning("No session deleted for: " + sessionId);
                return false;
            }

            log.info("Successfully deleted session: " + sessionId);
            return true;

        } catch (Exception e) {
            log.severe("Error deleting session: " + sessionId + ". Error: " + e.getMessage());
            throw new RuntimeException("Failed to delete session", e);
        }
    }


    // 根据用户ID获取会话列表
    @Transactional(readOnly = true)
    public List<Session> getSessionsByUserId(Long userId, int limit) {
        return sessionMapper.findSessionsByUserId(userId, limit);
    }

    // 获取指定 session_id 的所有消息
    @Transactional(readOnly = true)
    public List<Message> getMessagesBySessionId(UUID sessionId) {
        return messageMapper.findMessagesBySessionId(sessionId);
    }

    // 保存消息到数据库
    @Transactional  // 确保保存消息的操作是原子的
    public void saveMessage(Message message) {
        if (message.getMessageType() == null) {
            message.setMessageType("text"); // 设定一个默认值，例如 "text"
        }
        // 保存消息到数据库，直接调用 MyBatis-Plus 提供的 insert 方法
        messageMapper.insertMessage(message);
    }

    // 在 Service 层添加插入会话的方法
    @Transactional  // 确保数据库操作是原子的
    public boolean insertSession(Long userId, UUID sessionId, String sessionName) {
        // 创建一个新的会话对象
        Session session = new Session();
        session.setUserId(userId);
        session.setSessionId(sessionId.toString());  // 将 UUID 转为字符串保存
        session.setSessionName(sessionName);
        session.setCreatedAt(LocalDateTime.now());  // 设置创建时间
        session.setUpdatedAt(LocalDateTime.now());  // 设置更新时间
        session.setIsArchived(false);  // 默认未归档
        session.setLastMessage(null);  // 新会话没有消息

        try {
            // 调用 Mapper 层的 insertSession 方法进行插入操作
            boolean isInserted = sessionMapper.insertSession(session);

            if (!isInserted) {
                log.info("Session creation failed for sessionId: {"+session.getSessionId()+"}");
            }

            return isInserted;  // 返回插入结果
        } catch (Exception e) {
            // 记录异常日志并返回插入失败
            log.info("Error inserting session for userId: {" +session.getUserId()+"} sessionId: {"+session.getSessionId()+"}"+e);
            return false;
        }
    }

}
