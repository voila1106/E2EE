package com.voila.e2eechatserver.packet.s2c;

import com.voila.e2eechatserver.packet.CodecCombinedPacket;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.nio.ByteBuffer;

@NoArgsConstructor
@AllArgsConstructor
public class MessageStatusPacket extends CodecCombinedPacket<MessageStatusPacket> {
    /** 0 已发送，1 排队 */
    private byte status;

    @Override
    public ByteBuffer encode(MessageStatusPacket packet, int id){
        ByteBuffer buffer = ByteBuffer.allocate(5);
        buffer.putInt(id);
        buffer.put(packet.status);
        return buffer;
    }

    @Override
    public MessageStatusPacket decode(ByteBuffer data){
        return new MessageStatusPacket(data.get());
    }
}
