package com.voila.e2eechatserver.mapper;

import com.voila.e2eechatserver.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccountMapper {

    @Select("select * from user where id=#{id}")
    User getById(String id);

    @Insert("insert into user values (#{id},#{nickname},#{desc},#{pwHash},#{avatar},#{publicKey})")
    void register(User user);

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
