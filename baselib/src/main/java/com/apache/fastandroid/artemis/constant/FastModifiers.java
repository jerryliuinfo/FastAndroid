package com.apache.fastandroid.artemis.constant;

/**
 * author: 01370340
 * data: 2019/6/26
 * description:
 */
public class FastModifiers {
    /**
     * The {@code int} value representing the {@code public}
     * modifier.
     */
    public static final int PUBLIC           = 0x00000001;

    /**
     * The {@code int} value representing the {@code private}
     * modifier.
     */
    public static final int PRIVATE          = 0x00000002;

    /**
     * The {@code int} value representing the {@code protected}
     * modifier.
     */
    public static final int PROTECTED        = 0x00000004;

    /**
     * The {@code int} value representing the {@code static}
     * modifier.
     */
    public static final int STATIC           = 0x00000008;




    public static String toString(int mod) {
        StringBuilder sb = new StringBuilder();
        int len;

        if ((mod & PUBLIC) != 0)        sb.append("public ");
        if ((mod & PROTECTED) != 0)     sb.append("protected ");
        if ((mod & PRIVATE) != 0)       sb.append("private ");
        if ((mod & STATIC) != 0)        sb.append("static ");

        if ((len = sb.length()) > 0)    /* trim trailing space */
            return sb.toString().substring(0, len-1);
        return "";
    }
}
