package com.voila.e2eechatserver.entity;

public record SimpleMsg(int status, String msg) {
    public static final SimpleMsg OK = simpleMsg(200);
    public static SimpleMsg simpleMsg(int status, String msg){
        return new SimpleMsg(status, msg);
    }
    public static SimpleMsg simpleMsg(int status){
        return new SimpleMsg(status, "");
    }

}