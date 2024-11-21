package com.voila.e2eechatserver.common;

import com.voila.e2eechatserver.mapper.AccountMapper;
import com.voila.e2eechatserver.mapper.MessageQueueMapper;
import com.voila.e2eechatserver.mapper.UserConfigMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComponentManager {
    @Autowired
    private AccountMapper am;
    @Autowired
    private MessageQueueMapper mqm;
    @Autowired
    private UserConfigMapper ucm;

    public static AccountMapper accountMapper;
    public static MessageQueueMapper messageQueueMapper;
    public static UserConfigMapper userConfigMapper;

    @PostConstruct
    public void init() {
        accountMapper = am;
        messageQueueMapper = mqm;
        userConfigMapper = ucm;

        am.init();
        mqm.init();
        ucm.init();
    }

}
