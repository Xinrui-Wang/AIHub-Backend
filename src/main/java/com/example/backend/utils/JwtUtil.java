package com.example.backend.utils;

import io.jsonwebtoken.*;

import java.util.Date;

public class JwtUtil {

    // 用于签名的密钥
    private static final String SECRET_KEY = "your-secret-key";

    // 生成JWT
    public static String generateToken(Long userId) {
        return Jwts.builder()
                .setSubject(userId.toString())  // 将用户ID作为JWT的subject
                .setIssuedAt(new Date())  // 设置JWT的签发时间
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))  // 设置过期时间（1天）
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // 使用HMAC-SHA256算法进行签名
                .compact();
    }

    // 从JWT中提取用户ID
    public static Long extractUserId(String token) {
        return Long.parseLong(Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject());  // 从JWT中提取用户ID
    }

    // 检查Token是否过期
    public static boolean isTokenExpired(String token) {
        Date expirationDate = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expirationDate.before(new Date());
    }

    // 验证Token是否有效
    public static boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);  // 解析JWT，如果没有异常，说明有效
            return true;
        } catch (JwtException e) {
            return false;  // 验证失败，返回false
        }
    }
}
