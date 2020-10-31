package com.apache.fastandroid.artemis;

import androidx.annotation.IntDef;

import com.apache.fastandroid.artemis.support.bean.TypeInfoBean;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by 01370340 on 2017/10/17.
 */

public class TypeInfoManager {

    public static final int GET_ONE               = 0x00000002;

    public static final int GET_TWO               = 0x00000004;

    public static final int GET_THREE             = 0x00000008;

    public static final int GET_FOUR              = 0x00000010;

    public static final int GET_FIVE              = 0x00000020;

    public static final int GET_SIX               = 0x00000040;


    @IntDef(flag = true, value = {
            GET_ONE,
            GET_TWO,
            GET_THREE,
            GET_FOUR,
            GET_FIVE,
            GET_SIX,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface TypeInfoFlags {

    }


    public TypeInfoBean getTypeBean(@TypeInfoFlags int flags){
        TypeInfoBean bean = new TypeInfoBean();
        if ( (flags & TypeInfoManager.GET_ONE ) != 0) {
            bean.one = "one";
        }
        if ((flags & TypeInfoManager.GET_TWO ) != 0){
            bean.two = "two";
        }
        if ((flags & TypeInfoManager.GET_THREE ) != 0){
            bean.three = "three";
        }
        if ((flags & TypeInfoManager.GET_FOUR ) != 0){
            bean.four = "four";
        }
        if ((flags & TypeInfoManager.GET_FIVE ) != 0){
            bean.five = "five";
        }
        if ((flags & TypeInfoManager.GET_SIX ) != 0){
            bean.six = "six";
        }
        return bean;
    }
}
