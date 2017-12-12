package com.jay.im.client.util;

public class LUID {

    private static long luid;

    public static long getLuid() {
        return luid++;
    }

    public static void setLuid(long luid) {
        LUID.luid = luid;
    }
}
