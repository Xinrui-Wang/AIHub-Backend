package com.example.backend.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.model.Session;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

@Mapper
public interface SessionMapper extends BaseMapper<Session> {

    // 默认方法，查询用户会话列表
    default List<Session> findSessionsByUserId(Long userId, int limit) {
        return this.selectList(
                new QueryWrapper<Session>()
                        .eq("user_id", userId)
                        .last("LIMIT " + limit)
        );
    }

    // 使用硬编码的 SQL 插入会话数据
    @Insert("INSERT INTO all_user_chat_sessions (session_id, " +
            "user_id, session_name, created_at, updated_at, " +
            "is_archived) VALUES (#{sessionId}::UUID, #{userId}, " +
            "#{sessionName}, NOW(), NOW(), false)")
    boolean insertSession(@Param("session") Session session);
}

