package com.voila.e2eechatserver.controller;

import com.voila.e2eechatserver.entity.SimpleMsg;
import com.voila.e2eechatserver.entity.User;
import com.voila.e2eechatserver.mapper.AccountMapper;
import com.voila.e2eechatserver.validation.Password;
import com.voila.e2eechatserver.validation.PublicKey;
import com.voila.e2eechatserver.validation.UserId;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.apache.tomcat.util.buf.HexUtils;
import org.hibernate.validator.constraints.Length;
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
@Validated
public class AccountController {
    private static final String SALT = "f6dc9bd0-0c19-4ac4-c6b7-d4bb9ef44fa1";

    @Autowired
    AccountMapper accountMapper;

    @PostMapping("/register")
    public Object register(@UserId String id, @Length(min = 1,max = 80) String nickname,
                           @Password String password, @PublicKey String publicKey){

        if(accountMapper.getById(id) != null){
            return simpleMsg(400, "Account exists");
        }

        User user = User.builder().id(id).nickname(nickname).publicKey(publicKey).pwHash(encodePassword(password, id)).build();
        accountMapper.register(user);
        return SimpleMsg.OK;
    }

    @PostMapping("/login")
    public Object login(String id, String password, HttpServletRequest request){
        User user = accountMapper.getById(id);
        if(user == null){
            return simpleMsg(400, "User not found");
        }
        if(!encodePassword(password, id).equals(user.getPwHash())){
            return simpleMsg(400, "Wrong password");
        }

        request.getSession().setAttribute("user", user);
        return SimpleMsg.OK;
    }


    @SneakyThrows
    private String encodePassword(String password,String id) {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest((password + id + SALT).getBytes(StandardCharsets.UTF_8));
        return HexUtils.toHexString(hash);
    }
}
