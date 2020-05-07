package com.apache.fastandroid.sample;

/**
 * Created by Jerry on 2020-05-07.
 */
public class MultiFlagsSetDemo {
    public static final int CLICKABLE     = 0x01;
    public static final int FOCUSABLE     = 0x02;
    public static final int FLAG_03     = 0x04;
    public static final int FLAG_04     = 0x08;
    public static final int FLAG_05     = 0x10;
    public static final int FLAG_06     = 0x20;

    int mViewFlags;

    public void setFlags(int flags, int mask) {
        int old = mViewFlags;
        // mViewFlags & ~mask；将之前的非mask位的值取出来， mask位置0，   flags & mask：取出 mask位的值
        mViewFlags = (mViewFlags & ~mask) | (flags & mask);

        //异或处理
        int changed = mViewFlags ^ old;
        if (changed == 0) {
            return;
        }

    }

    public boolean isClickable(){
        return (mViewFlags & CLICKABLE) == CLICKABLE;

    }

    public void setClickable(boolean enableFlag1) {
        setFlags(enableFlag1 ? CLICKABLE : 0, CLICKABLE);
    }

    public boolean isFocusable(){
        return (mViewFlags & FOCUSABLE) == CLICKABLE;

    }

    public void setFocusable(boolean enableFlag1) {
        setFlags(enableFlag1 ? FOCUSABLE : 0, CLICKABLE);
    }



}
