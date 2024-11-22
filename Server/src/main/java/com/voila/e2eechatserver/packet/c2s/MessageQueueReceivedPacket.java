package com.voila.e2eechatserver.packet.c2s;

import com.voila.e2eechatserver.common.ComponentManager;
import com.voila.e2eechatserver.entity.User;
import com.voila.e2eechatserver.packet.CodecCombinedPacket;
import org.springframework.web.socket.WebSocketSession;

import java.nio.ByteBuffer;

public class MessageQueueReceivedPacket extends CodecCombinedPacket<MessageQueueReceivedPacket> {
    @Override
    public ByteBuffer encode(MessageQueueReceivedPacket packet, int id){
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(id);
        return buffer;
    }

    @Override
    public MessageQueueReceivedPacket decode(ByteBuffer data){
        return new MessageQueueReceivedPacket();
    }

    @Override
    public void handle(WebSocketSession session){
        ComponentManager.messageQueueMapper.clear(((User)(session.getAttributes().get("user"))).getId());
    }
}
