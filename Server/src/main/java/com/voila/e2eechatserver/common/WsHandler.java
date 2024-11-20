package com.voila.e2eechatserver.common;

import com.voila.e2eechatserver.entity.User;
import com.voila.e2eechatserver.packet.Packet;
import com.voila.e2eechatserver.util.PacketUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.nio.ByteBuffer;

public class WsHandler extends AbstractWebSocketHandler {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) throws Exception{
        User user = (User) session.getAttributes().get("user");
        user.setWsSession(session);
    }

    @Override
    protected void handleBinaryMessage(@NotNull WebSocketSession session, @NotNull BinaryMessage message) throws Exception{
        if(message.getPayloadLength() < 4){
            return;
        }
        ByteBuffer byteBuffer = message.getPayload();
        Packet<?> packet = PacketUtils.decode(byteBuffer);
        if(packet != null){
            packet.handle();
        }
    }
}
