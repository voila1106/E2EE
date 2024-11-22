package com.voila.e2eechatserver.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface FriendMapper {
    @Select("select 1 from friend where one=#{one} and two=#{two}")
    boolean isFriend(String one,String two);

    @Select("select 1 from pending_invitation where one=#{one} and two=#{two}")
    boolean isRequiringFriend(String one,String two);

    @Insert("insert into pending_invitation values (#{one},#{two})")
    void requireFriend(String one,String two);

    @Delete("delete from pending_invitation where one=#{one} and two=#{two}")
    void requireEnd(String one, String two);

    @Insert("insert into friend(one, two) values (#{one},#two);insert into friend(two, one) values (#{one},#two)")
    void addFriend(String one, String two);

    @Update("update friend set remark=#{remark} where one=#{one} and two=#{two}")
    void remark(String one, String two, byte[] remark);

    @Insert("""
create table if not exists friend
(
    one varchar(25) references user (id) on delete cascade ,
    two varchar(25) references user (id) on delete cascade ,
    remark blob,
    primary key (one,two)
);
create table if not exists pending_invitation
(
    one    varchar(25) references user (id) on delete cascade,
    two    varchar(25) references user (id) on delete cascade,
    primary key (one, two)
);
""")
    void init();

}
