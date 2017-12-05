package com.apache.fastandroid.delegate;

import com.apache.fastandroid.delegate.impl.LoginSuccess;
import com.apache.fastandroid.delegate.impl.Logout;
import com.apache.fastandroid.delegate.impl.ModuleNovelFactoryName;
import com.apache.fastandroid.delegate.impl.ModuleTopicFactoryName;
import com.apache.fastandroid.delegate.impl.ModuleUsercenterFactoryName;
import com.apache.fastandroid.delegate.impl.StartMainActivity;
import com.apache.fastandroid.delegate.impl.WatchLeakCancaryRef;
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
    /**
     * 创建Topic模块的实现类
     */
    public static final String ACTION_CREATE_MODULE_TOPIC_FACTORY = "com.apache.fastandroid:topic";
    /**
     * 创建User模块的实现类
     */
    public static final String ACTION_CREATE_MODULE_USER_FACTORY = "com.apache.fastandroid:usercenter";



    public static final String ACTION_START_MAINACTIVITY = "startMainActivity";
    public static final String ACTION_LOGIN= "login";
    public static final String ACTION_LOGOUT= "logout";

    public static final String ACTION_WATCH_LEAK_CANCARY= "watchLeakCancary";
    @Override
    public IDataDelegate getDataTransfer(String action) {
        return null;
    }

    @Override
    public IActionDelegate getActionTransfer(String action) {
        switch (action){
            case ACTION_START_MAINACTIVITY:
                return new StartMainActivity();
            case ACTION_LOGIN:
                return new LoginSuccess();
            case ACTION_LOGOUT:
                return new Logout();
            case ACTION_WATCH_LEAK_CANCARY:
                return new WatchLeakCancaryRef();
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
            case ACTION_CREATE_MODULE_NOVEL_FACTORY:
                return new ModuleNovelFactoryName();
            case ACTION_CREATE_MODULE_TOPIC_FACTORY:
                return new ModuleTopicFactoryName();
            case ACTION_CREATE_MODULE_USER_FACTORY:
                return new ModuleUsercenterFactoryName();

            default:
                return null;
        }
    }
}
