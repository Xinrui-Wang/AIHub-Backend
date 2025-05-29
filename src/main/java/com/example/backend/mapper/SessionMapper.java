package com.example.backend.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.backend.model.Session;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface SessionMapper extends BaseMapper<Session> {

    // 查询用户会话列表（带分页）
    default List<Session> findSessionsByUserId(Long userId, int limit) {
        return this.selectList(
                new QueryWrapper<Session>()
                        .eq("user_id", userId)
                        .orderByDesc("updated_at")
                        .last("LIMIT " + limit)
        );
    }

    // 插入新会话（PostgreSQL 语法）
    @Insert("INSERT INTO all_user_chat_sessions (session_id, user_id, session_name, " +
            "created_at, updated_at, is_archived) " +
            "VALUES (#{session.sessionId}::UUID, #{session.userId}, #{session.sessionName}, " +
            "NOW(), NOW(), false)")
    boolean insertSession(@Param("session") Session session);

    // 根据ID查询会话（包含锁机制）
    @Select("SELECT * FROM all_user_chat_sessions WHERE session_id = #{sessionId}::UUID FOR UPDATE")
    Session findSessionById(@Param("sessionId") String sessionId);

    // 删除会话（返回影响行数）
    @Delete("DELETE FROM all_user_chat_sessions WHERE session_id = #{sessionId}::UUID")
    int deleteById(@Param("sessionId") String sessionId);

    // 批量删除会话（扩展方法）
    @Delete("<script>" +
            "DELETE FROM all_user_chat_sessions WHERE session_id IN " +
            "<foreach item='id' collection='sessionIds' open='(' separator=',' close=')'>" +
            "#{id}::UUID" +
            "</foreach>" +
            "</script>")
    int batchDelete(@Param("sessionIds") List<String> sessionIds);
}