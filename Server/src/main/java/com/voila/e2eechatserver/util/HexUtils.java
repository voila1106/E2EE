package com.voila.e2eechatserver.util;

public class HexUtils {
    private static final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes){
        char[] hexChars = new char[bytes.length * 2];
        for(int j = 0; j < bytes.length; j++){
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_CHARS[v >>> 4];
            hexChars[j * 2 + 1] = HEX_CHARS[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] hexToBytes(String hex){
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i < hex.length(); i += 2){
            bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
        }
        return bytes;
    }
}
