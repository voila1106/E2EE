package com.voila.e2eechatserver.packet;

public interface Packet<T extends Packet<T>> {
    Codec<T> codec();

    void handle();
}
