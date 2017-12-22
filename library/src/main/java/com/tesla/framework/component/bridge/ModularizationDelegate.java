package com.tesla.framework.component.bridge;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.tesla.framework.applike.IApplicationLike;
import com.tesla.framework.common.util.log.NLog;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jerryliu on 2017/7/11.
 * 负责各个模块间的数据交互
 */

public class ModularizationDelegate {
    private static final String ILLEGAL_PARAMETER = "非法路由参数";
    public static final String TAG = ModularizationDelegate.class.getSimpleName();

    //key: groupAritifact  value:IDelegateFactory
    private final Map<String,IDelegateFactory> mFactoryMap = new ConcurrentHashMap<>();


   
    private ModularizationDelegate(){}
    private static class SingletonHolder{
        private SingletonHolder() {
        }

        private  static final ModularizationDelegate INSTANCE = new ModularizationDelegate();
    }
    public static ModularizationDelegate getInstance() {
    
        return SingletonHolder.INSTANCE;
    }


    public void register(String key, IDelegateFactory factory){
        mFactoryMap.put(key,factory);
    }

    public void unRegister(String key){
        if (mFactoryMap.containsKey(key)){
            mFactoryMap.remove(key);
        }
    }


    public static void registerComponent(String className, Context context){
        try {
            Class clz = Class.forName(className);
            IApplicationLike applicationLike = (IApplicationLike) clz.newInstance();
            applicationLike.onCreate(context);
        } catch (Exception e) {
            NLog.printStackTrace(e);
        }
    }

    public static void unRegisterComponent(String className){
        try {
            Class clz = Class.forName(className);
            IApplicationLike applicationLike = (IApplicationLike) clz.newInstance();
            applicationLike.onStop();
        } catch (Exception e) {
            NLog.printStackTrace(e);
        }
    }

    

    /**
     *
     * @param groupArtifactAction  传过来的参数格式必须为groupId:artifactId:action的形式
     *                                例如 com.sgs.unite:moduleB:getOrderInfo
     * @param args
     * @param extras
     * @return
     * @throws DelegateException
     */
    public Bundle getData(String groupArtifactAction, Bundle args, Object... extras) throws DelegateException {
        if (TextUtils.isEmpty(groupArtifactAction)){
            throw new DelegateException(ILLEGAL_PARAMETER);
        }
        String[] params = groupArtifactAction.split(":");
        if (params.length != 3){
            throw new DelegateException(ILLEGAL_PARAMETER);
        }

        //获取groupArtifact
        String groupArtifact = groupArtifactAction.substring(0,groupArtifactAction.lastIndexOf(':'));
        //解析action
        String action = groupArtifactAction.substring(groupArtifactAction.lastIndexOf(':')+1,groupArtifactAction.length());

        IDelegateFactory factory = mFactoryMap.get(groupArtifact);
        if (factory != null) {
            IDataDelegate transfer = factory.getDataTransfer(action);

            if (transfer != null) {
                return transfer.getData(args, extras);
            }
        }else {
            String factoryName = getCreateFactoryName(groupArtifact);
            IDelegateFactory delegateFactory = getDelegateFactory(factoryName);
            if (delegateFactory != null){
                mFactoryMap.put(groupArtifact,delegateFactory);
                IDataDelegate transfer = delegateFactory.getDataTransfer(action);
                if (transfer != null){
                    return transfer.getData(args,extras);
                }
            }
        }

        throw new DelegateException("unknow data transfer");
    }


    public Object getObjectData(String group_artifact_action, Bundle args, Object... extras) throws DelegateException {
        return getObjectData(group_artifact_action,null,args,extras);
    }

    /**
     *
     * @param group_artifact_action  传过来的参数格式必须为groupId:artifactId:action的形式
     *                                例如 com.sgs.unite:moduleB:getOrderInfo
     * @param args
     * @param extras
     * @return
     * @throws DelegateException
     */
    public Object getObjectData(String group_artifact_action, IActionDelegate.IActionCallback callback, Bundle args, Object... extras) throws DelegateException {
        if (TextUtils.isEmpty(group_artifact_action)){
            throw new DelegateException(ILLEGAL_PARAMETER);
        }
        String[] params = group_artifact_action.split(":");
        if (params.length != 3){
            throw new DelegateException(ILLEGAL_PARAMETER);
        }

        //获取groupArtifact
        String groupArtifact = group_artifact_action.substring(0,group_artifact_action.lastIndexOf(':'));
        //解析action
        String action = group_artifact_action.substring(group_artifact_action.lastIndexOf(':')+1,group_artifact_action.length());

        IDelegateFactory factory = mFactoryMap.get(groupArtifact);
        if (factory != null) {
            IObjectDataDelegate transfer = factory.getObjectDataTransfer(action);

            if (transfer != null) {
                return transfer.getObjectData(args,callback, extras);
            }
        }else {
            String factoryName = getCreateFactoryName(groupArtifact);
            IDelegateFactory delegateFactory = getDelegateFactory(factoryName);
            if (delegateFactory != null){
                mFactoryMap.put(groupArtifact,delegateFactory);
                IObjectDataDelegate transfer = delegateFactory.getObjectDataTransfer(action);
                if (transfer != null){
                    return transfer.getObjectData(args,callback,extras);
                }
            }
        }

        throw new DelegateException("unknow data transfer");
    }


    /**
     *
     * @param groupArtifactAction,  传过来的参数格式必须为groupId:artifactId:action的形式
     *                                例如 com.sgs.unite:moduleA:startActivity
     * @param args
     * @param callback
     * @param extras
     */
    public void runStaticAction(String groupArtifactAction, Bundle args, IActionDelegate.IActionCallback callback, Object... extras) throws DelegateException {
        if (TextUtils.isEmpty(groupArtifactAction)){
            throw new DelegateException(ILLEGAL_PARAMETER);
        }
        String[] params = groupArtifactAction.split(":");
        if (params.length != 3){
            throw new DelegateException(ILLEGAL_PARAMETER);
        }

        //获取groupArtifact
        String groupArtifact = groupArtifactAction.substring(0,groupArtifactAction.lastIndexOf(':'));
        //解析action
        String action = groupArtifactAction.substring(groupArtifactAction.lastIndexOf(':')+1,groupArtifactAction.length());

        //根据groupArtifact找到对应的aar模块
        IDelegateFactory factory = mFactoryMap.get(groupArtifact);
        if (factory != null) {

            //调用对应aar模块的action方法
            IActionDelegate transfer = factory.getActionTransfer(action);

            if (transfer != null) {
                transfer.runAction(args, callback, extras);
            }
        }else{
            String factoryName = getCreateFactoryName(groupArtifact);
            IDelegateFactory delegateFactory = getDelegateFactory(factoryName);
            if (delegateFactory != null){
                mFactoryMap.put(groupArtifact,delegateFactory);

                IActionDelegate transfer = delegateFactory.getActionTransfer(action);
                if (transfer != null){
                    transfer.runAction(args,callback,extras);
                }
            }
        }
    }



    public IDelegateFactory getDelegateFactory(String factoryName) throws DelegateException {
        if (!TextUtils.isEmpty(factoryName)){
            Class clz = null;
            try {
                clz = Class.forName(factoryName);
            } catch (ClassNotFoundException e) {
                NLog.printStackTrace(e);
            }
            if (clz != null && IDelegateFactory.class.isAssignableFrom(clz)){
                IDelegateFactory delegateFactory = null;
                try {
                    delegateFactory = (IDelegateFactory) clz.newInstance();
                } catch (InstantiationException e) {
                    NLog.printStackTrace(e);
                } catch (IllegalAccessException e) {
                    NLog.printStackTrace(e);

                }
                return delegateFactory;
            }else {
                throw new DelegateException(String.format("clz name %s 没有实现IDelegateFactory接口",factoryName));
            }
        }
        return null;
    }



    /**
     * 告诉主模块需要创建哪个model的delegateFactory
     * @param groupArtifact
     * @return
     */
    public String getCreateFactoryName(String groupArtifact) throws DelegateException {
        IDelegateFactory mainDelegateFactory = mFactoryMap.get("com.apache.fastandroid:moduleMain");
        //通过主模块去获取工厂，达到实现懒加载功能
        if (mainDelegateFactory != null && mainDelegateFactory instanceof ICreateFactory){
            ICreateFactory createFactory = (ICreateFactory) mainDelegateFactory;
            IDataDelegate iDataDelegate =  createFactory.getDelegateFactoryName(groupArtifact);
            if (iDataDelegate != null){
                Bundle bundle = iDataDelegate.getData(null);
                if (bundle != null){
                    return bundle.getString("result");
                }
            }

        }

        return null;
    }
}
