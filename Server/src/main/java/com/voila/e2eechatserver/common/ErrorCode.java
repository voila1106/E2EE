package com.voila.e2eechatserver.common;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public enum ErrorCode {
    INVALID_PUBLIC_KEY(1001,"Invalid public key"),
    INVALID_PASSWORD(1002,"Invalid password"),;

    private static final Map<Integer,ErrorCode> VALUES;
    static {
        ImmutableMap.Builder<Integer, ErrorCode> builder = ImmutableMap.builder();
        for (ErrorCode errorCode : ErrorCode.values()) {
            builder.put(errorCode.code, errorCode);
        }
        VALUES = builder.build();
    }

    public final int code;
    public final String message;

    ErrorCode(int code, String message){
        this.code = code;
        this.message = message;
    }

    public static ErrorCode get(int code){
        return VALUES.get(code);
    }
}
