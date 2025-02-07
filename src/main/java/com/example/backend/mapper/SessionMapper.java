package com.example.backend.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.model.Session;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface SessionMapper extends BaseMapper<Session> {
    default List<Session> findSessionsByUserId(Long userId, int limit) {
        return this.selectList(
                new QueryWrapper<Session>()
                        .eq("user_id", userId)
                        .last("LIMIT " + limit)
        );
    }
}
