package com.voila.e2eechatserver.mapper;

import com.voila.e2eechatserver.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/** 登录后的用户操作 */
@Mapper
public interface UserMapper {
    @Update("update user set nickname=#{nickname},desc=#{desc},avatar=#{avatar} where id=#{id}")
    void updateProfile(User user);
}
