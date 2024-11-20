package com.voila.e2eechatserver.packet;

import java.nio.ByteBuffer;

public interface Codec<T extends Packet<T>>{
    ByteBuffer encode(T packet);
    T decode(ByteBuffer data);
}
