package com.apache.fastandroid.delegate;

import com.tesla.framework.component.bridge.IActionDelegate;
import com.tesla.framework.component.bridge.ICreateFactory;
import com.tesla.framework.component.bridge.IDataDelegate;
import com.tesla.framework.component.bridge.IDelegateFactory;
import com.tesla.framework.component.bridge.IObjectDataDelegate;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class MainDelegateFactory implements IDelegateFactory,ICreateFactory {

    /**
     * 创建Novel模块的实现类
     */
    public static final String ACTION_CREATE_MODULE_NOVEL_FACTORY = "com.apache.fastandroid:novel";



    @Override
    public IDataDelegate getDataTransfer(String action) {
        return null;
    }

    @Override
    public IActionDelegate getActionTransfer(String action) {
        switch (action){
            default:
                return null;
        }
    }

    @Override
    public IObjectDataDelegate getObjectDataTransfer(String action) {
        return null;
    }

    @Override
    public IDataDelegate getDelegateFactoryName(String groupArtifact) {
        switch (groupArtifact){

            default:
                return null;
        }
    }
}
