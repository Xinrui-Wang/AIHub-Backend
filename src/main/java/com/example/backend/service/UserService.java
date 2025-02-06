package com.example.backend.service;

import com.example.backend.mapper.UserMapper;
import com.example.backend.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserMapper userMapper;

    /**
     * 用于对用户密码进行加密和验证的工具类，基于 BCrypt 哈希算法。
     */
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 构造器注入，强制依赖声明。
     *
     * @param userMapper 数据访问层对象，用于查询用户信息。
     */
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * 用户登录方法，根据邮箱或手机号查询用户信息，并验证密码是否正确。
     *
     * @param userEmail 用户输入的邮箱（可为空）
     * @param userPhoneNumber 用户输入的手机号（可为空）
     * @param password 用户输入的明文密码
     * @return 如果验证成功，返回对应的用户对象；否则返回 null
     */
    public User login(String userEmail, String userPhoneNumber, String password) {
        // 根据邮箱或手机号获取用户信息
        User user = getUserByEmailOrPhone(userEmail, userPhoneNumber);

        if (user == null) {
            // 用户未找到，记录日志并返回 null
            logger.info("User not found for: email={}, phone={}", userEmail, userPhoneNumber);
            return null;
        }

        // 记录用户已找到的信息
        logger.info("User nickname: {}", user.getNickname());

        // 验证密码是否匹配
        if (passwordMatches(password, user.getPasswordHash())) {
            logger.info("Password match result: true");
            return user;
        }

        // 密码不匹配，记录日志并返回 null
        logger.info("Password does not match");
        return null;
    }

    /**
     * 根据用户ID查询用户。
     *
     * @param userId 用户ID
     * @return 对应的用户对象，如果未找到则返回 null
     */
    public User getUserById(Long userId) {
        User user = userMapper.selectById(userId); // 使用MyBatis的selectById方法查询
        if (user != null) {
            logger.info("User found: {}", user.getNickname());
        } else {
            logger.info("User not found for ID: {}", userId);
        }
        return user;
    }

    /**
     * 根据邮箱或手机号查询用户。
     *
     * @param userEmail 用户输入的邮箱
     * @param userPhoneNumber 用户输入的手机号
     * @return 对应的用户对象，如果未找到则返回 null
     */
    private User getUserByEmailOrPhone(String userEmail, String userPhoneNumber) {
        if (userEmail != null) {
            // 根据邮箱查询
            return userMapper.selectByEmail(userEmail);
        } else if (userPhoneNumber != null) {
            // 根据手机号查询
            return userMapper.selectByPhoneNumber(userPhoneNumber);
        }
        return null;
    }

    /**
     * 验证明文密码与加密后的密码是否匹配。
     *
     * @param rawPassword 用户输入的明文密码
     * @param hashedPassword 数据库中存储的加密密码
     * @return 如果匹配返回 true，否则返回 false
     */
    private boolean passwordMatches(String rawPassword, String hashedPassword) {
        // 使用 BCryptPasswordEncoder 进行密码匹配验证
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}
