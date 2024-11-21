package com.voila.e2eechatserver.util;

import com.google.common.collect.ImmutableMap;
import com.voila.e2eechatserver.common.ComponentManager;
import com.voila.e2eechatserver.common.UserManager;
import com.voila.e2eechatserver.entity.User;
import com.voila.e2eechatserver.mapper.MessageQueueMapper;
import com.voila.e2eechatserver.packet.Codec;
import com.voila.e2eechatserver.packet.Packet;
import com.voila.e2eechatserver.packet.c2s.EditProfilePacket;
import com.voila.e2eechatserver.packet.c2s.MessageQueueReceivedPacket;
import com.voila.e2eechatserver.packet.common.FriendRequestPacket;
import com.voila.e2eechatserver.packet.s2c.MessageStatusPacket;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Log4j2
public class PacketUtils {

    public static Map<Integer, Codec<? extends Packet<?>>> codecById = new ImmutableMap.Builder<Integer, Codec<?>>()
        .put(0, FriendRequestPacket.CODEC)
        .put(1, MessageQueueReceivedPacket.CODEC)
        .put(2, MessageStatusPacket.CODEC)
        .put(3, EditProfilePacket.CODEC)
        .build();
    public static final Map<Codec<?>, Integer> codecToId;

    static{
        ImmutableMap.Builder<Codec<? extends Packet<?>>, Integer> toId = new ImmutableMap.Builder<>();
        for(Map.Entry<Integer, Codec<?>> entry : codecById.entrySet()){
            toId.put(entry.getValue(), entry.getKey());
        }
        codecToId = toId.build();
    }

    public static Packet<?> decode(ByteBuffer data){
        int id = data.getInt();
        Codec<? extends Packet<?>> codec = codecById.get(id);
        if(codec == null){
            log.warn("Unknown packet id {}", id);
            return null;
        }
        try{
            return codec.decode(data);
        }catch(Exception e){
            log.warn("Failed to decode: {}", codec);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends Packet<T>> ByteBuffer encode(T packet){
        int id = codecToId.get(packet.codec());
        Codec<T> codec = (Codec<T>) codecById.get(id);
        return codec.encode(packet, id).position(0);
    }

    public static void putString(String str, ByteBuffer buffer) {
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        buffer.putInt(bytes.length);
        buffer.put(bytes);
    }

    public static String getString(ByteBuffer buffer) {
        int length = buffer.getInt();
        byte[] bytes = new byte[length];
        buffer.get(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static <T extends Packet<T>> void sendPacket(T packet, WebSocketSession wsSession){
        BinaryMessage msg = new BinaryMessage(encode(packet));
        try{
            wsSession.sendMessage(msg);
        }catch(IOException e){
            log.catching(e);
        }
    }

    public static <T extends Packet<T>> void sendPacket(T packet, String sendToId){
        User user = UserManager.loggedInUsers.get(sendToId);
        if(user == null){ // 不在线
            MessageQueueMapper mqMapper = ComponentManager.messageQueueMapper;
            int maxId = mqMapper.getMaxId();
            mqMapper.enqueue(sendToId, maxId + 1, encode(packet).array());
        }else {
            sendPacket(packet, user.getWsSession());
        }
    }
}
