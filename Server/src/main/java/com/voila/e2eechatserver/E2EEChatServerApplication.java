package com.voila.e2eechatserver;

import com.voila.e2eechatserver.mapper.AccountMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class E2EEChatServerApplication {
	@Autowired
	AccountMapper accountMapper;

	public static void main(String[] args) {
		SpringApplication.run(E2EEChatServerApplication.class, args);
	}

	@PostConstruct
	public void init() {
		accountMapper.init();
	}
}
