package com.voila.e2eechatserver.packet;

import org.springframework.web.socket.WebSocketSession;

public interface Packet<T extends Packet<T>> {
    Codec<T> codec();

    void handle(WebSocketSession session);
}
