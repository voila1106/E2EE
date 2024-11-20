package com.voila.e2eechatserver;

import lombok.SneakyThrows;
import org.apache.tomcat.util.buf.HexUtils;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class MiscTest {

    @SneakyThrows
    @Test
    void genPubKey() {
        KeyPairGenerator keyGen=KeyPairGenerator.getInstance("EC");
        keyGen.initialize(256);
        KeyPair keyPair=keyGen.generateKeyPair();
        System.out.println(HexUtils.toHexString(keyPair.getPublic().getEncoded()));

    }

    @Test
    void ascii(){
        for(int i = 0x21; i < 0x7f; i++){
            System.out.println((char) i);
        }
    }

    @Test
    void regex(){
        System.out.println("".matches("[a-zA-Z0-9_]+"));
    }

    @Test
    void byteBuffer(){
        ByteBuffer buf=ByteBuffer.allocate(4);
        buf.putInt(20);
        System.out.println(HexUtils.toHexString(buf.array()));
    }

}
