package com.voila.e2eechatserver.packet.c2s;

import com.voila.e2eechatserver.common.ComponentManager;
import com.voila.e2eechatserver.entity.User;
import com.voila.e2eechatserver.packet.CodecCombinedPacket;
import com.voila.e2eechatserver.packet.s2c.FriendRequestPacketS2C;
import com.voila.e2eechatserver.util.PacketUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.nio.ByteBuffer;

@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestPacketC2S extends CodecCombinedPacket<FriendRequestPacketC2S> {
    private String to;
    private String message;
    /** hash(sharedSecret) */
    private byte[] validation;

    @Override
    public ByteBuffer encode(FriendRequestPacketC2S packet, int id){
        return ByteBuffer.allocate(0);
    }

    @Override
    public FriendRequestPacketC2S decode(ByteBuffer data){
        String to = PacketUtils.getString(data);
        String message = PacketUtils.getString(data);
        int len = data.getInt();
        byte[] v = new byte[len];
        data.get(v);
        return new FriendRequestPacketC2S(to, message, v);
    }

    @Override
    public void handle(WebSocketSession session){
        if(!ComponentManager.userMapper.userExists(to)) return;
        User fromUser = PacketUtils.getUser(session);
        if(!fromUser.getId().equals(to) // 不能加自己
            && !ComponentManager.friendMapper.isFriend(fromUser.getId(), to) // 已经加了不能重复加
            && !ComponentManager.friendMapper.isRequiringFriend(fromUser.getId(), to) // 请求过了不能重复请求
            && ComponentManager.userConfigMapper.acceptRequest(to)){ // 用户设置接受请求
            PacketUtils.sendPacket(new FriendRequestPacketS2C(fromUser,message,validation), to);
            ComponentManager.friendMapper.requireFriend(fromUser.getId(), to);
        }
    }
}
