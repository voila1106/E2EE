package com.voila.e2eechatserver.controller;

import com.voila.e2eechatserver.entity.SimpleMsg;
import com.voila.e2eechatserver.entity.User;
import com.voila.e2eechatserver.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 登录后的用户操作 */
@Validated
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserMapper userMapper;

    @PostMapping("/edit")
    public Object editProfile(User user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("user");
        loginUser.update(user);
        // TODO: 确认有权限访问avatar指向的文件
        userMapper.updateProfile(loginUser);
        return SimpleMsg.OK;
    }
}
