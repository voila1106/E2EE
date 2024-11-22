package com.voila.e2eechatserver.config;

import com.voila.e2eechatserver.common.WsHandler;
import com.voila.e2eechatserver.interceptor.WsHandshakeInterceptor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
public class WsConfig implements WebSocketConfigurer, WebSocketMessageBrokerConfigurer {
    @Autowired
    WsHandler wsHandler;

    @Override
    public void registerWebSocketHandlers(@NotNull WebSocketHandlerRegistry registry){
        registry.addHandler(wsHandler,"/ws").addInterceptors(new WsHandshakeInterceptor()).setAllowedOrigins("*");
    }
}
