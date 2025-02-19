package com.example.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.backend.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * MessageMapper 接口用于操作数据库中的聊天消息表。
 * 该接口继承自 MyBatis-Plus 的 BaseMapper<Message>，提供了基本的 CRUD 操作，
 * 还定义了查询指定会话 ID 关联的消息记录以及插入新消息的 SQL 语句。
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

    /**
     * 根据会话 ID 查询所有相关的消息。
     *
     * @param sessionId 会话的唯一标识符（UUID 格式）。
     * @return 该会话的所有消息列表。
     */
    @Select("SELECT message_id, session_id, message_type, " +
            "message_content, created_at, sender " +
            "FROM all_user_chat_session_messages " +
            "WHERE session_id = CAST(#{sessionId} AS UUID)")
    List<Message> findMessagesBySessionId(@Param("sessionId") UUID sessionId);

    /**
     * 插入一条新的聊天消息。
     *
     * 该 SQL 语句在插入时，会将 session_id 从 String 映射为 UUID，
     * 并且 message_type 需要匹配数据库中的枚举类型（message_type_enum）。
     *
     * @param message 要插入的消息对象，包含消息内容、创建时间、发送者、消息类型等字段。
     */
    @Insert("INSERT INTO all_user_chat_session_messages " +
            "(session_id, message_content, created_at, sender, message_type) " +
            "VALUES (#{sessionId}::UUID, #{messageContent}, " +
            "#{createdAt}, #{sender}, #{messageType}::message_type_enum)")
    void insertMessage(Message message);
}
