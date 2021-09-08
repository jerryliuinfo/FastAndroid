package com.apache.fastandroid.demo.designmode.observer;

import com.tesla.framework.common.util.log.NLog;

/**
 * Created by Jerry on 2021/8/11.
 */
public class Component3 implements IControlComponent {
    public static final String OBSEVER_MODE_TAG = "observer";
    @Override
    public void onPlayStateChanged(int playState) {
        NLog.d(OBSEVER_MODE_TAG, "Component3 onPlayStateChanged playState: %s",playState);
    }

    @Override
    public void attach(ControlWrapper mControlWrapper) {
        NLog.d(OBSEVER_MODE_TAG, "Component3 attach");
    }
}