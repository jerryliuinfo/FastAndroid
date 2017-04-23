package com.tesla.framework.common.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by jerryliu on 2017/4/23.
 */

public class IOUtil {
    private IOUtil(){

    }
    public static void close(Closeable closeable){
        if (closeable != null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                closeable = null;
            }
        }
    }
}
