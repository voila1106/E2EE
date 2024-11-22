package com.voila.e2eechatserver.packet.common;

import com.voila.e2eechatserver.common.ComponentManager;
import com.voila.e2eechatserver.entity.User;
import com.voila.e2eechatserver.packet.CodecCombinedPacket;
import com.voila.e2eechatserver.util.PacketUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.nio.ByteBuffer;

@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestResponsePacket extends CodecCombinedPacket<FriendRequestResponsePacket> {
    private String from;
    private String to;
    /** 0 accepted, 1 denied, 2 corrupted */
    private byte result;

    @Override
    public ByteBuffer encode(FriendRequestResponsePacket packet, int id){
        ByteBuffer buffer = ByteBuffer.allocate(10240);
        buffer.putInt(id);
        PacketUtils.putString(from, buffer);
        PacketUtils.putString(to, buffer);
        buffer.put(packet.result);
        buffer.limit(buffer.position());
        return buffer;
    }

    @Override
    public FriendRequestResponsePacket decode(ByteBuffer data){
        String from = PacketUtils.getString(data);
        String to = PacketUtils.getString(data);
        byte r = data.get();
        return new FriendRequestResponsePacket(from, to, r);
    }

    @Override
    public void handle(WebSocketSession session){
        User loginUser = PacketUtils.getUser(session);
        if(!loginUser.getId().equals(from) || !ComponentManager.friendMapper.isRequiringFriend(to, from)) return;
        if(result == 0){
            ComponentManager.friendMapper.addFriend(from, to);
        }
        ComponentManager.friendMapper.requireEnd(to, from);
        PacketUtils.sendPacket(this, to);
    }
}
