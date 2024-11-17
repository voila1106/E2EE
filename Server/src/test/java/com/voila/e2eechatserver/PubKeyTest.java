package com.voila.e2eechatserver;

import lombok.SneakyThrows;
import org.apache.tomcat.util.buf.HexUtils;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class PubKeyTest {

    @SneakyThrows
    @Test
    void genPubKey() {
        KeyPairGenerator keyGen=KeyPairGenerator.getInstance("EC");
        keyGen.initialize(256);
        KeyPair keyPair=keyGen.generateKeyPair();
        System.out.println(HexUtils.toHexString(keyPair.getPublic().getEncoded()));

    }
}
