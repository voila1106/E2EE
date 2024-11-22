package com.voila.e2eechatserver.common;

import com.voila.e2eechatserver.entity.User;
import com.voila.e2eechatserver.mapper.MessageQueueMapper;
import com.voila.e2eechatserver.mapper.UserMapper;
import com.voila.e2eechatserver.packet.Packet;
import com.voila.e2eechatserver.util.PacketUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class WsHandler extends AbstractWebSocketHandler {
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    UserMapper mapper;
    @Autowired
    MessageQueueMapper mqMapper;

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) throws Exception{
        User user = (User) session.getAttributes().get("user");
        user.setWsSession(session);
        UserManager.login(user);

        List<byte[]> mq = mqMapper.getAll(user.getId());
        for(byte[] bytes : mq){
            session.sendMessage(new BinaryMessage(bytes));
        }
        session.sendMessage(new BinaryMessage("EOQ".getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) throws Exception{
        UserManager.logout((User) session.getAttributes().get("user"));
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
                packet.handle(session);
            }catch(Exception e){
                LOGGER.catching(e);
            }
        }
    }
}
