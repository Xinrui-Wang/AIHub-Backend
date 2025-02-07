package com.example.backend.service;

import com.example.backend.mapper.SessionMapper;
import com.example.backend.model.Session;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {

    private final SessionMapper sessionMapper;

    public SessionService(SessionMapper sessionMapper) {
        this.sessionMapper = sessionMapper;
    }

    // 根据用户ID获取会话列表
    public List<Session> getSessionsByUserId(Long userId, int limit) {
        // 假设通过 SessionMapper 查询会话列表
        return sessionMapper.findSessionsByUserId(userId, limit);
    }
}
