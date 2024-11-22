package com.voila.e2eechatserver.packet.s2c;

import com.voila.e2eechatserver.entity.User;
import com.voila.e2eechatserver.packet.CodecCombinedPacket;
import com.voila.e2eechatserver.util.PacketUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.nio.ByteBuffer;

@AllArgsConstructor
@NoArgsConstructor
public class FriendRequestPacketS2C extends CodecCombinedPacket<FriendRequestPacketS2C> {
    private User from;
    private String message;
    /** hash(sharedSecret) */
    private byte[] validation;

    @Override
    public ByteBuffer encode(FriendRequestPacketS2C packet, int id){
        ByteBuffer buffer = ByteBuffer.allocate(10 * 1024);
        buffer.putInt(id);
        packet.from.writeToBuffer(buffer);
        PacketUtils.putString(packet.message, buffer);
        PacketUtils.putBytes(validation, buffer);
        buffer.limit(buffer.position());
        return buffer;
    }

    @Override
    public FriendRequestPacketS2C decode(ByteBuffer data){
        return null;
    }
}
