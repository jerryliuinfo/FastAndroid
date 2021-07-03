package com.apache.fastandroid.demo.temp;

/**
 * Created by 01370340 on 2018/5/4.
 */

public enum SilentMode {

    RESTART(1),
    RECOVER_ACTIVITY_STACK(2),
    RECOVER_TOP_ACTIVITY(3),
    RESTART_AND_CLEAR(4);

    int value;
    SilentMode(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public static SilentMode getMode(int value){
        switch (value){
            case 1:
                return RESTART;
            case 3:
                return RECOVER_TOP_ACTIVITY;
            case 4:
                return RESTART_AND_CLEAR;
            default:
                return RECOVER_ACTIVITY_STACK;
        }
    }
}
