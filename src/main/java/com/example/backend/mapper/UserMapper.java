package com.example.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.backend.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据邮箱查询用户
     *
     * @param userEmail 用户邮箱
     * @return 对应的用户对象，如果未找到则返回 null
     */
    @Select("SELECT * FROM user_accounts WHERE user_email = #{userEmail}")
    User selectByEmail(@Param("userEmail") String userEmail);

    /**
     * 根据手机号查询用户
     *
     * @param userPhoneNumber 用户手机号
     * @return 对应的用户对象，如果未找到则返回 null
     */
    @Select("SELECT * FROM user_accounts WHERE user_phone_number = #{userPhoneNumber}")
    User selectByPhoneNumber(@Param("userPhoneNumber") String userPhoneNumber);
}
