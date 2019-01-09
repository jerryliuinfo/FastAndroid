package com.apache.fastandroid.support.config;

import android.content.Context;

import com.tesla.framework.common.util.sp.BaseSharedPreferenceConfigManager;

/**
 * Created by jerryliu on 2017/6/30.
 * 一些通用信息的配置
 */

public class CommonConfigManager extends BaseSharedPreferenceConfigManager {

    public static final String SP_NAME = "sp_common";
    
    private static CommonConfigManager instance = null;

    private CommonConfigManager(Context context) {
        super(context);
    }

    public static CommonConfigManager getInstance(Context context) {
        if (instance == null) {
            synchronized (CommonConfigManager.class) {
                if (instance == null){
                    instance = new CommonConfigManager(context);
                }
            }
        }
        return instance;
    }
    
    @Override
    public String configSPFileName() {
        return SP_NAME;
    }
    


}
