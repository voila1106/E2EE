package com.voila.e2eechatserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User {
    String id;
    String nickname;
    String desc;
    String pwHash;
    String avatar;
    String publicKey;

    WebSocketSession wsSession;

    public void update(User other){
        nickname=other.nickname;
        desc=other.desc;
        avatar=other.avatar;
    }
}
