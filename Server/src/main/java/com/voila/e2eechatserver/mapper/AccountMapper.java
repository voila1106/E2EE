package com.voila.e2eechatserver.mapper;

import com.voila.e2eechatserver.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper {
    int checkAccountCount(String id);

    void register(User user);
}
