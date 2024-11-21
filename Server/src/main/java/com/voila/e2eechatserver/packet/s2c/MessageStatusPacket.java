package com.voila.e2eechatserver.packet.s2c;

import com.voila.e2eechatserver.packet.Codec;
import com.voila.e2eechatserver.packet.Packet;
import lombok.AllArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.nio.ByteBuffer;

@AllArgsConstructor
public class MessageStatusPacket implements Packet<MessageStatusPacket> {
    /** 0 已发送，1 排队 */
    private byte status;
    public static final Codec<MessageStatusPacket> CODEC = new Codec<>() {
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
    };
    @Override
    public Codec<MessageStatusPacket> codec(){
        return CODEC;
    }

    @Override
    public void handle(WebSocketSession session){
    }
}
