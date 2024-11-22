package com.voila.e2eechatserver.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserConfigMapper {
    @Insert("insert into user_config(id) values (#{id})")
    void register(String id);

    @Select("select acceptRequest from user_config where id=#{id}")
    boolean acceptRequest(String id);

    @Insert("""
create table if not exists user_config (
    id varchar(25) primary key references user on delete cascade,
    acceptRequest boolean default 1,
    acceptDMFromGroup boolean default 1,
    autoJoinGroup boolean default 1
)
""")
    void init();
}
