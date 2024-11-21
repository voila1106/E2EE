package com.voila.e2eechatserver.common;

import com.voila.e2eechatserver.entity.User;

import java.util.HashMap;

public class UserManager {
    public static final HashMap<String, User> loggedInUsers = new HashMap<>();
    public static void login(User user) {
        loggedInUsers.put(user.getId(), user);
    }
    public static void logout(User user) {
        loggedInUsers.remove(user.getId());
    }
}
