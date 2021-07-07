package com.sh.pojo.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHashing {

    public static String encode(String inputPassword) {
        byte[] password = inputPassword.getBytes();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < 10000; i++) {
            md.update(password);			// 해싱한 문자열을 md에 저장
            password = md.digest();		   // md 객체의 다이제스트를 얻어 password 갱신
        }
        return byteToString(password);
    }
    // 바이트 -> 16진수
    private static String byteToString(byte[] password) {
        StringBuilder sb = new StringBuilder();
        for(byte a : password) {
            sb.append(String.format("%02x", a));
        }
        return sb.toString();
    }

}
