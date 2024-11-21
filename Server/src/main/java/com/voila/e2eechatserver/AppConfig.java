package com.voila.e2eechatserver;

import com.voila.e2eechatserver.common.WsHandler;
import com.voila.e2eechatserver.interceptor.LoginInterceptor;
import com.voila.e2eechatserver.interceptor.WsHandshakeInterceptor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class AppConfig implements WebMvcConfigurer, WebSocketConfigurer {

    @Autowired
    LoginInterceptor loginInterceptor;

    @Autowired
    WsHandler wsHandler;

    @Override
    public void addInterceptors(@NotNull InterceptorRegistry registry){
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/account/**");
    }

    @Override
    public void registerWebSocketHandlers(@NotNull WebSocketHandlerRegistry registry){
        registry.addHandler(wsHandler,"/ws").addInterceptors(new WsHandshakeInterceptor()).setAllowedOrigins("*");
    }
}
