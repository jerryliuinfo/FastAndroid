package com.apache.fastandroid.artemis.bridge;

import android.os.Bundle;

/**
 * Created by jerryliu on 2017/7/11.
 */

public interface IObjectDataDelegate {

    /**
     * 用于返回一些Bundle不能返回的数据  例如一个Fragment, Bitmap或者一个Observable等非基本类型,非序列化
     * 非Parceable等类型的数据
     * @param args
     * @param extras
     * @return
     */
    Object getObjectData(Bundle args, IActionDelegate.IActionCallback callback, Object... extras);
}
