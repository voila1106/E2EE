package com.voila.e2eechatserver.packet.common;

import com.voila.e2eechatserver.packet.Codec;
import com.voila.e2eechatserver.packet.Packet;

import java.nio.ByteBuffer;

public class FriendRequestPacket implements Packet<FriendRequestPacket> {
    public static final Codec<FriendRequestPacket> CODEC = new Codec<>() {
        @Override
        public ByteBuffer encode(FriendRequestPacket packet){
            return null;
        }

        @Override
        public FriendRequestPacket decode(ByteBuffer data){
            return null;
        }
    };

    @Override
    public Codec<FriendRequestPacket> codec(){
        return CODEC;
    }

    @Override
    public void handle(){

    }
}
