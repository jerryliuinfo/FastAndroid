package com.apache.fastandroid.demo.designmode.observer;

import com.tesla.framework.common.util.log.NLog;

/**
 * Created by Jerry on 2021/8/11.
 */
public class StandardController extends BaseController{



    /**
     * 快速添加各个组件
     */
    public void addDefaultControlComponent() {
        Component1 component1 = new Component1();
        Component2 component2 = new Component2();
        Component3 component3 = new Component3();
        addControlComponent(component1,component2, component3);
    }

    @Override
    protected void onPlayStateChanged(int playState) {
        super.onPlayStateChanged(playState);
        NLog.d(Component1.OBSEVER_MODE_TAG, "StandardController onPlayStateChanged playState: %s",playState);
        switch (playState) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }
}
