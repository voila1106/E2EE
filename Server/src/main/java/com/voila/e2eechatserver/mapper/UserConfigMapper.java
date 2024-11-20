package com.voila.e2eechatserver.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserConfigMapper {

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
