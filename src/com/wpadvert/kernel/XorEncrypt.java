package com.wpadvert.kernel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * 异或加密
 * Created by SCWANG on 2017/5/20.
 */

public class XorEncrypt {

    public static byte[] encrypt(CharSequence input, CharSequence key) throws IOException {
        return encrypt(getBytes(input),key);
    }

    public static byte[] encrypt(byte[] input, CharSequence key) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream(input.length);
        encrypt(new ByteArrayInputStream(input), output, getBytes(key));
        return output.toByteArray();
    }

    public static void encrypt(InputStream input, OutputStream output, CharSequence key) throws IOException {
        encrypt(input,output, getBytes(key));
    }

    public static void encrypt(InputStream input, OutputStream output, byte[] key) throws IOException {
        byte[] bytes = new byte[1024];
        for(int len,kl = key.length; (len = input.read(bytes)) > 0;)
        {
            for (int i = 0; i < len; i++) {
                bytes[i] = (byte) ((len + key[i % kl] + i - key[i % kl] / len) ^ bytes[i]);
            }
            output.write(bytes, 0, len);
        }
    }

    private static byte[] getBytes(CharSequence key) throws UnsupportedEncodingException {
        return key.toString().getBytes("UTF-8");
    }

}
