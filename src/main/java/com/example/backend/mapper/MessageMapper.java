package com.example.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.backend.model.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {

    // 查询会话消息（按时间排序）
    @Select("SELECT * FROM all_user_chat_session_messages " +
            "WHERE session_id = #{sessionId}::UUID " +
            "ORDER BY created_at DESC")
    List<Message> findMessagesBySessionId(@Param("sessionId") UUID sessionId);

    // 插入消息（PostgreSQL 语法）
    @Insert("INSERT INTO all_user_chat_session_messages " +
            "(session_id, message_type, message_content, created_at, sender) " +
            "VALUES (#{sessionId}::UUID, #{messageType}::message_type_enum, " +
            "#{messageContent}, NOW(), #{sender})")
    void insertMessage(Message message);

    // 删除会话关联消息
    @Delete("DELETE FROM all_user_chat_session_messages " +
            "WHERE session_id = #{sessionId}::UUID")
    int deleteBySessionId(@Param("sessionId") UUID sessionId);

    // 带条件删除消息（扩展方法）
    @Delete("<script>" +
            "DELETE FROM all_user_chat_session_messages " +
            "WHERE session_id = #{sessionId}::UUID " +
            "<if test='beforeDate != null'>" +
            "AND created_at &lt;= #{beforeDate}" +
            "</if>" +
            "</script>")
    int deleteByCondition(@Param("sessionId") UUID sessionId,
                          @Param("beforeDate") String beforeDate);

    // 消息统计
    @Select("SELECT COUNT(*) FROM all_user_chat_session_messages " +
            "WHERE session_id = #{sessionId}::UUID")
    int countBySessionId(@Param("sessionId") UUID sessionId);
}