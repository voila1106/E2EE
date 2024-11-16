package com.voila.e2eechatserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
