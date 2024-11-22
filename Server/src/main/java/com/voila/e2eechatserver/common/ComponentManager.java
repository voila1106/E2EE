package com.voila.e2eechatserver.common;

import com.voila.e2eechatserver.mapper.FriendMapper;
import com.voila.e2eechatserver.mapper.MessageQueueMapper;
import com.voila.e2eechatserver.mapper.UserConfigMapper;
import com.voila.e2eechatserver.mapper.UserMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComponentManager {
    @Autowired
    private UserMapper am;
    @Autowired
    private MessageQueueMapper mqm;
    @Autowired
    private UserConfigMapper ucm;
    @Autowired
    private FriendMapper fm;

    public static UserMapper userMapper;
    public static MessageQueueMapper messageQueueMapper;
    public static UserConfigMapper userConfigMapper;
    public static FriendMapper friendMapper;

    @PostConstruct
    public void init() {
        userMapper = am;
        messageQueueMapper = mqm;
        userConfigMapper = ucm;
        friendMapper = fm;

        am.init();
        mqm.init();
        ucm.init();
        fm.init();
    }

}
