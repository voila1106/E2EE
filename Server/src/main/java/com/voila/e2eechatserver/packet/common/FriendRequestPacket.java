package com.voila.e2eechatserver.packet.common;

import com.voila.e2eechatserver.packet.Codec;
import com.voila.e2eechatserver.packet.Packet;
import com.voila.e2eechatserver.util.PacketUtils;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.nio.ByteBuffer;

@Data
public class FriendRequestPacket implements Packet<FriendRequestPacket> {
    public static final Codec<FriendRequestPacket> CODEC = new Codec<>() {
        @Override
        public ByteBuffer encode(FriendRequestPacket packet, int id){
            ByteBuffer buffer = ByteBuffer.allocate(10 * 1024);
            buffer.putInt(id);
            PacketUtils.putString(packet.from, buffer);
            PacketUtils.putString(packet.to, buffer);
            PacketUtils.putString(packet.message, buffer);
            buffer.limit(buffer.position());
            return buffer;
        }

        /** @param data 此处应传入已经读完id的buffer */
        @Override
        public FriendRequestPacket decode(ByteBuffer data){
            String from = PacketUtils.getString(data);
            String to = PacketUtils.getString(data);
            String message = PacketUtils.getString(data);
            return new FriendRequestPacket(from, to, message);
        }
    };

    private String from;
    private String to;
    private String message;

    public FriendRequestPacket(String from, String to, String message){
        this.from = from;
        this.to = to;
        this.message = message;
    }


    @Override
    public Codec<FriendRequestPacket> codec(){
        return CODEC;
    }

    @Override
    public void handle(WebSocketSession session){
        PacketUtils.sendPacket(this, to);
    }
}
