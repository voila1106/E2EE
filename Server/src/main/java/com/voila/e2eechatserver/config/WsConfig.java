package com.voila.e2eechatserver.config;

import com.voila.e2eechatserver.common.WsHandler;
import com.voila.e2eechatserver.interceptor.WsHandshakeInterceptor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WsConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(@NotNull WebSocketHandlerRegistry registry){
        registry.addHandler(new WsHandler(),"/ws").addInterceptors(new WsHandshakeInterceptor()).setAllowedOrigins("*");
    }
}
