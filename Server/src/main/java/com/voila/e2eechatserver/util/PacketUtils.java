package com.voila.e2eechatserver.util;

import com.google.common.collect.ImmutableMap;
import com.voila.e2eechatserver.packet.Codec;
import com.voila.e2eechatserver.packet.Packet;
import com.voila.e2eechatserver.packet.common.FriendRequestPacket;
import lombok.extern.log4j.Log4j2;

import java.nio.ByteBuffer;
import java.util.Map;

@Log4j2
public class PacketUtils {

    public static Map<Integer, Codec<? extends Packet<?>>> codecById = new ImmutableMap.Builder<Integer, Codec<?>>()
        .put(0, FriendRequestPacket.CODEC)
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
        return codec.encode(packet);
    }
}
