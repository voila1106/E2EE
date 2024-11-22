package com.voila.e2eechatserver.packet;

import java.nio.ByteBuffer;

public interface Codec<T extends Packet<T>>{
    /** C2S一般不用实现 */
    ByteBuffer encode(T packet,int id);

    /** S2C一般不用实现
     *  @param data 此处应传入已经读完id的buffer */
    T decode(ByteBuffer data);
}
