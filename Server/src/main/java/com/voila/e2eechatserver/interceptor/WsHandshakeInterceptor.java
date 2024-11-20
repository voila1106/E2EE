package com.voila.e2eechatserver.interceptor;

import com.voila.e2eechatserver.entity.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.OriginHandshakeInterceptor;

import java.util.Map;

public class WsHandshakeInterceptor extends OriginHandshakeInterceptor {
    @Override
    public boolean beforeHandshake(@NotNull ServerHttpRequest request, @NotNull ServerHttpResponse response, @NotNull WebSocketHandler wsHandler, @NotNull Map<String, Object> attributes) throws Exception{
        if(!super.beforeHandshake(request, response, wsHandler, attributes)){
            return false;
        }
        if(request instanceof ServletServerHttpRequest servletRequest){
            User user = (User) servletRequest.getServletRequest().getSession(true).getAttribute("user");
            if(user == null){
                return false;
            }
            attributes.put("user", user);
            return true;
        }
        return false;
    }
}
