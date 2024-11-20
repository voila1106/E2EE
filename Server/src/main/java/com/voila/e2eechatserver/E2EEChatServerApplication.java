package com.voila.e2eechatserver;

import com.voila.e2eechatserver.mapper.AccountMapper;
import com.voila.e2eechatserver.mapper.UserConfigMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class E2EEChatServerApplication {

	AccountMapper accountMapper;
	UserConfigMapper userConfigMapper;

	@Autowired
	public E2EEChatServerApplication(AccountMapper accountMapper, UserConfigMapper userConfigMapper) {
		this.accountMapper = accountMapper;
		this.userConfigMapper = userConfigMapper;
	}

	public static void main(String[] args) {
		SpringApplication.run(E2EEChatServerApplication.class, args);
	}

	@PostConstruct
	public void init() {
		accountMapper.init();
		userConfigMapper.init();
	}
}
