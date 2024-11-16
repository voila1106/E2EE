package com.voila.e2eechatserver.controller;

import com.voila.e2eechatserver.entity.SimpleMsg;
import com.voila.e2eechatserver.entity.User;
import com.voila.e2eechatserver.mapper.AccountMapper;
import com.voila.e2eechatserver.util.HexUtils;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotEmpty;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import static com.voila.e2eechatserver.entity.SimpleMsg.simpleMsg;

@RestController
@RequestMapping("/account")
public class AccountController {
    private static final String SALT = "f6dc9bd0-0c19-4ac4-c6b7-d4bb9ef44fa1";

    @Autowired
    AccountMapper accountMapper;

    @PostMapping("/register")
    public Object register(@NotEmpty String id,@NotEmpty String nickname,
                           @NotEmpty String password,@NotEmpty String publicKey) {
        if(accountMapper.checkAccountCount(id) > 0){
            return simpleMsg(400, "account exists");
        }
        User user=User.builder().id(id).nickname(nickname).publicKey(publicKey).pwHash(encodePassword(password)).build();
        accountMapper.register(user);
        return SimpleMsg.OK;
    }

    @SneakyThrows
    private String encodePassword(String password) {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        return HexUtils.bytesToHex(hash);
    }
}
