package com.voila.e2eechatserver.packet;

import org.springframework.web.socket.WebSocketSession;

public interface Packet<T extends Packet<T>> {
    Codec<T> codec();

    /** S2C的包一般不用重写 */
    default void handle(WebSocketSession session){}
}
