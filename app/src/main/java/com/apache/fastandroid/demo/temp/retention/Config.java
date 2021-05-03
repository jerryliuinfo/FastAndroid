package com.apache.fastandroid.demo.temp.retention;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * Created by Jerry on 2021/5/2.
 */
public class Config {

    @IntDef({Config.LoginType.LEFT, Config.LoginType.CENTER,
            Config.LoginType.RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LoginType {
        int LEFT = 0;
        int CENTER = 1;
        int RIGHT = 2;
    }
}
