package com.voila.e2eechatserver.mapper;

import com.voila.e2eechatserver.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/** 登录前的操作 */
@Mapper
public interface UserMapper {

    @Select("select * from user where id=#{id}")
    User getById(String id);

    @Insert("insert into user(id, nickname, desc, pwHash, avatar, publicKey) values (#{id},#{nickname},#{desc},#{pwHash},#{avatar},#{publicKey})")
    void register(User user);

    @Update("update user set nickname=#{nickname},desc=#{desc},avatar=#{avatar} where id=#{id}")
    void updateProfile(User user);

    @Select("select 1 from user where id=#{id}")
    boolean userExists(String id);


    @Insert("""
create table if not exists user(
    id varchar(20) primary key ,
    nickname varchar(40),
    desc varchar(200),
    pwHash varchar(64),
    avatar varchar(200),
    publicKey varchar(64)
);
""")
    void init();

}
