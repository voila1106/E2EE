package com.voila.e2eechatserver.packet.c2s;

import com.voila.e2eechatserver.common.ComponentManager;
import com.voila.e2eechatserver.entity.User;
import com.voila.e2eechatserver.packet.CodecCombinedPacket;
import com.voila.e2eechatserver.packet.s2c.MessageStatusPacket;
import com.voila.e2eechatserver.util.PacketUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.nio.ByteBuffer;

@AllArgsConstructor
@NoArgsConstructor
public class EditProfilePacket extends CodecCombinedPacket<EditProfilePacket> {
    @Override
    public ByteBuffer encode(EditProfilePacket packet, int id){
        ByteBuffer buffer = ByteBuffer.allocate(10 * 1024);
        buffer.putInt(id);
        PacketUtils.putString(packet.nickname, buffer);
        PacketUtils.putString(packet.desc, buffer);
        PacketUtils.putString(packet.avatar, buffer);
        buffer.limit(buffer.position());
        return buffer;
    }

    @Override
    public EditProfilePacket decode(ByteBuffer data){
        return new EditProfilePacket(PacketUtils.getString(data), PacketUtils.getString(data), PacketUtils.getString(data));
    }

    private String nickname;
    private String avatar;
    private String desc;

    @Override
    public void handle(WebSocketSession session){
        User user = (User) session.getAttributes().get("user");
        user.setNickname(nickname);
        // TODO: 检查权限
        user.setAvatar(avatar);
        user.setDesc(desc);
        ComponentManager.userMapper.updateProfile(user);
        PacketUtils.sendPacket(new MessageStatusPacket((byte) 0), user.getWsSession());
    }
}
