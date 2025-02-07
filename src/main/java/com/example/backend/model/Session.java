package com.example.backend.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.backend.config.TableNames;

import java.time.LocalDateTime;

@TableName(TableNames.SESSION) // 映射到 all_user_chat_sessions 表
public class Session {

    @TableId  // 主键字段
    private Long sessionId;  // 会话 ID

    private Long userId;  // 用户 ID

    private String sessionName;  // 会话名称

    private LocalDateTime createdAt;  // 创建时间

    private LocalDateTime updatedAt;  // 更新时间

    private Boolean isArchived;  // 是否归档，f 表示未归档，t 表示已归档

    private String lastMessage;  // 最后一个消息

    // getter 和 setter 方法
    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public String toString() {
        return "Session{" +
                "sessionId=" + sessionId +
                ", userId=" + userId +
                ", sessionName='" + sessionName + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", isArchived=" + isArchived +
                ", lastMessage='" + lastMessage + '\'' +
                '}';
    }
}
