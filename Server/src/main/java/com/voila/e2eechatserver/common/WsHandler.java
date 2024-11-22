package com.voila.e2eechatserver.common;

import com.voila.e2eechatserver.entity.User;
import com.voila.e2eechatserver.packet.Packet;
import com.voila.e2eechatserver.util.PacketUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WsHandler extends AbstractWebSocketHandler {
    private static final Logger LOGGER = LogManager.getLogger();

    Map<WebSocketSession, ConcurrentWebSocketSessionDecorator> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) throws Exception{
        User user = PacketUtils.getUser(session);
        ConcurrentWebSocketSessionDecorator concurrentSession = new ConcurrentWebSocketSessionDecorator(session, 20000, 1024 * 1024);
        sessions.put(session, concurrentSession);
        user.setWsSession(concurrentSession);
        UserManager.login(user);

        List<byte[]> mq = ComponentManager.messageQueueMapper.getAll(user.getId());
        for(byte[] bytes : mq){
            concurrentSession.sendMessage(new BinaryMessage(bytes));
        }
        concurrentSession.sendMessage(new BinaryMessage("EOQ".getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) throws Exception{
        UserManager.logout(PacketUtils.getUser(session));
        sessions.remove(session);
    }

    @Override
    protected void handleBinaryMessage(@NotNull WebSocketSession session, @NotNull BinaryMessage message) throws Exception{
        if(message.getPayloadLength() < 4){
            return;
        }
        ByteBuffer byteBuffer = message.getPayload();
        Packet<?> packet = PacketUtils.decode(byteBuffer);
        if(packet != null){
            try{
                packet.handle(sessions.get(session));
            }catch(Exception e){
                LOGGER.catching(e);
            }
        }
    }
}
