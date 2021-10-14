package com.tesla.framework.common.util;

/**
 * Created by Jerry on 2021/9/24.
 */
public class GlobalConfig {

    public boolean isEncrypt;


    private static volatile GlobalConfig instance = null;
    private GlobalConfig(){}
    public static GlobalConfig getInstance() {
        if (instance == null) {
            synchronized (GlobalConfig.class) {
                if (instance == null){
                    instance = new GlobalConfig();
                }
            }
        }
        return instance;
    }
}
