package com.voila.e2eechatserver.packet;

public abstract class CodecCombinedPacket<T extends Packet<T>> implements Packet<T>, Codec<T>{
    @Override
    public Codec<T> codec(){
        return this;
    }
}
