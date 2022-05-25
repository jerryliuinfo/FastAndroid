package com.apache.fastandroid.component.spkiller;

import android.content.Context;

/**
 * created by Knight-ZXW on 2021/9/14
 */
public interface HiddenApiExempter {

    /**
     * 设置 允许调用HiddenApi
     * @return
     */
    boolean exempt(Context context);

}
