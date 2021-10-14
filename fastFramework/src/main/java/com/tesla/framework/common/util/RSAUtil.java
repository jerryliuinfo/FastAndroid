package com.tesla.framework.common.util;

import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

/**
 * description:
 * author chaojiong.zhang
 * data: 2021/6/29
 * copyright TCL+
 */
public class RSAUtil {
    /**
     * RSA最大加密密文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * 和服务端相同的加密方式
     */
    private static final String RSA = "RSA";

    //数据 RSA 加密， Bouncy Castle 的默认 RSA 实现是 “RSA/None/NoPadding”。 android 内置了部分的 Bouncy Castle 代码
    public static String encodeNoPadding(String content) {
        String base64Result = null;
        try {
            // “RSA/None/NoPadding”
            Cipher cipher = Cipher.getInstance(RSA);
//            cipher.init(Cipher.ENCRYPT_MODE, JWTHelper.getParsedPublicKey());
            byte[] encryptedData = sectionDoFinal(content.getBytes(), cipher);
            base64Result = Base64.encodeToString(encryptedData, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64Result;
    }


    //数据 RSA 加密，标准 jce加密
    public static String encodePKCS1Padding(String content) {
        String base64Result = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//            cipher.init(Cipher.ENCRYPT_MODE, JWTHelper.getParsedPublicKey());
            byte[] encryptedData = sectionDoFinal(content.getBytes(), cipher);
            base64Result = Base64.encodeToString(encryptedData, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64Result;
    }





    /**
     * 分段加解密核心逻辑
     */
    private static byte[] sectionDoFinal(byte[] bytes, Cipher cipher) throws IllegalBlockSizeException, BadPaddingException, IOException {

        int inputLen = bytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加解
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(bytes, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(bytes, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }
}
