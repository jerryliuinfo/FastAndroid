package com.apache.fastandroid.artemis.comBridge;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jerryliu on 2017/7/11.
 * 负责各个模块间的数据交互
 */

public class ModularizationDelegate {
    public static final String TAG = ModularizationDelegate.class.getSimpleName();
    private static ModularizationDelegate instance = null;

    //key: groupAritifact  value:IDelegateFactory
    private final Map<String,IDelegateFactory> mFactoryMap = new ConcurrentHashMap<>();


    private ModularizationDelegate(){}
    public static ModularizationDelegate getInstance() {
        if (instance == null) {
            synchronized (ModularizationDelegate.class) {
                if (instance == null){
                    instance = new ModularizationDelegate();
                }
            }
        }
        return instance;
    }


    public void register(String key, IDelegateFactory factory){
        mFactoryMap.put(key,factory);
    }

    private void unRegister(String key){
        if (mFactoryMap.containsKey(key)){
            mFactoryMap.remove(key);
        }
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
    public Bundle getData(String group_artifact_action, Bundle args, Object... extras) throws Exception {
        if (TextUtils.isEmpty(group_artifact_action)){
            throw new DelegateException("非法路由参数");
        }
        String[] params = group_artifact_action.split(":");
        if (params.length != 3){
            throw new DelegateException("非法路由参数");
        }

        //获取groupArtifact
        String groupArtifact = group_artifact_action.substring(0,group_artifact_action.lastIndexOf(":"));
        //解析action
        String action = group_artifact_action.substring(group_artifact_action.lastIndexOf(":")+1,group_artifact_action.length());

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


    /**
     *
     * @param group_artifact_action  传过来的参数格式必须为groupId:artifactId:action的形式
     *                                例如 com.sgs.unite:moduleB:getOrderInfo
     * @param args
     * @param extras
     * @return
     * @throws DelegateException
     */
    public Object getObjectData(String group_artifact_action, Bundle args, Object... extras) throws Exception {
        if (TextUtils.isEmpty(group_artifact_action)){
            throw new DelegateException("非法路由参数");
        }
        String[] params = group_artifact_action.split(":");
        if (params.length != 3){
            throw new DelegateException("非法路由参数");
        }

        //获取groupArtifact
        String groupArtifact = group_artifact_action.substring(0,group_artifact_action.lastIndexOf(":"));
        //解析action
        String action = group_artifact_action.substring(group_artifact_action.lastIndexOf(":")+1,group_artifact_action.length());

        IDelegateFactory factory = mFactoryMap.get(groupArtifact);
        if (factory != null) {
            IObjectDataDelegate transfer = factory.getObjectDataTransfer(action);

            if (transfer != null) {
                return transfer.getObjectData(args, extras);
            }
        }else {
            String factoryName = getCreateFactoryName(groupArtifact);
            IDelegateFactory delegateFactory = getDelegateFactory(factoryName);
            if (delegateFactory != null){
                mFactoryMap.put(groupArtifact,delegateFactory);
                IObjectDataDelegate transfer = delegateFactory.getObjectDataTransfer(action);
                if (transfer != null){
                    return transfer.getObjectData(args,extras);
                }
            }
        }

        throw new DelegateException("unknow data transfer");
    }


    /**
     *
     * @param group_artifact_action,  传过来的参数格式必须为groupId:artifactId:action的形式
     *                                例如 com.sgs.unite:moduleA:startActivity
     * @param args
     * @param callback
     * @param extras
     */
    public void runStaticAction(String group_artifact_action, Bundle args, IActionDelegate.IActionCallback callback, Object... extras) throws Exception {
        if (TextUtils.isEmpty(group_artifact_action)){
            throw new DelegateException("非法路由参数");
        }
        String[] params = group_artifact_action.split(":");
        if (params.length != 3){
            throw new DelegateException("非法路由参数");
        }

        //获取groupArtifact
        String groupArtifact = group_artifact_action.substring(0,group_artifact_action.lastIndexOf(":"));
        //解析action
        String action = group_artifact_action.substring(group_artifact_action.lastIndexOf(":")+1,group_artifact_action.length());

        //根据groupArtifact找到对应的aar模块
        IDelegateFactory factory = mFactoryMap.get(groupArtifact);
        if (factory != null) {
            Log.d(TAG, "runStaticAction groupArtifact 缓存实例已存在 ");

            //调用对应aar模块的action方法
            IActionDelegate transfer = factory.getActionTransfer(action);

            if (transfer != null) {
                transfer.runAction(args, callback, extras);
            }
        }else{
            Log.d(TAG, "runStaticAction groupArtifact 缓存实例不存在 ");
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



    public IDelegateFactory getDelegateFactory(String factoryName) throws Exception {
        if (!TextUtils.isEmpty(factoryName)){
            Class clz = Class.forName(factoryName);
            if (IDelegateFactory.class.isAssignableFrom(clz)){
                IDelegateFactory delegateFactory = (IDelegateFactory) clz.newInstance();
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
    public String getCreateFactoryName(String groupArtifact) throws Exception {
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
