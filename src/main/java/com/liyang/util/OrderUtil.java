package com.liyang.util;

import java.util.UUID;

public class OrderUtil {
    public static String generateSn()
    {
        return  UUID.randomUUID().toString().split("-")[4];
    }
}
