package com.example.backend.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.backend.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    default User selectByEmail(String email) {
        return this.selectOne(new QueryWrapper<User>().eq("user_email", email));
    }

    default User selectByPhoneNumber(String phoneNumber) {
        return this.selectOne(new QueryWrapper<User>().eq("user_phone_number", phoneNumber));
    }
}
