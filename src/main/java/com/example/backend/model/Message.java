package com.example.backend.model;

import com.baomidou.mybatisplus.annotation.*;
import com.example.backend.config.TableNames;

import java.time.LocalDateTime;
import java.util.UUID;

@TableName(TableNames.SESSION_MESSAGES) // 指定表名
public class Message {

    @TableId(value = "message_id", type = IdType.AUTO)  // 主键自增
    private Long messageId;

    @TableField("session_id")
    private UUID sessionId;

    @TableField("message_type")
    private String messageType;  // 建议改成 enum 类型

    @TableField("message_content")
    private String messageContent;

    @TableField(value = "created_at", fill = FieldFill.INSERT)  // 插入时填充
    private LocalDateTime createdAt = LocalDateTime.now();  // 默认值

    @TableField("sender")
    private String sender;  // 只能是 "user" 或 "system"

    // Getter 和 Setter 方法
    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", sessionId=" + sessionId +
                ", messageType='" + messageType + '\'' +
                ", messageContent='" + messageContent + '\'' +
                ", createdAt=" + createdAt +
                ", sender='" + sender + '\'' +
                '}';
    }
}
