package com.jacob.common.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Util {

    public static String decode(String input){
        byte[] bytes = Base64.getDecoder().decode(input);

        return new String(bytes, StandardCharsets.UTF_8);
    }
}
