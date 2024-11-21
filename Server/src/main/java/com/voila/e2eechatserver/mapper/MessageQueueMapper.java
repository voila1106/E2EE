package com.voila.e2eechatserver.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageQueueMapper {

    @Insert("""
        create table if not exists message_queue
        (
            owner  varchar(25) primary key references user on delete cascade,
            id     int,
            data   blob
        );
        """)
    void init();

    @Insert("insert into message_queue values (#{owner},#{id},#{data})")
    void enqueue(String owner,int id, byte[] data);

    @Select("select max(id) from message_queue")
    int getMaxId();

    @Select("select data from message_queue where owner=#{owner}")
    List<byte[]> getAll(String owner);

    @Delete("delete from message_queue where owner=#{owner}")
    void clear(String owner);
}
