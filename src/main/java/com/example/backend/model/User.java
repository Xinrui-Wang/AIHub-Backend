package com.example.backend.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;

@TableName("user_accounts") // 映射到数据库中的 user_accounts 表
public class User {

    @TableId(type = IdType.AUTO) // 自动生成的主键
    private Long id;

    private String nickname;
    private String userEmail;        // 修改后的字段名，匹配数据库
    private String userPhoneNumber;  // 修改后的字段名，匹配数据库
    private String passwordHash;     // 存储加密后的密码
    private LocalDateTime createdAt; // 使用 LocalDateTime 类型
    private LocalDateTime updatedAt; // 使用 LocalDateTime 类型

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
