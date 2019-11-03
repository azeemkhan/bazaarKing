package com.tresshop.engine.base.utils;

import java.util.Base64;

public class EncodeDecode {

    public static String encodeToBase64(String string){
        return  Base64.getEncoder()
                .encodeToString(string.getBytes());
    }
    public static String deoodeFromBase64(String string){
        byte[] actualByte = Base64.getDecoder()
                .decode(string);
        return new String(actualByte);
    }
}
