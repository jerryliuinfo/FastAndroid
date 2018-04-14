package com.apache.fastandroid.artemis.rx;

import android.content.Context;

import com.tesla.framework.applike.FrameworkApplication;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by 01370340 on 2018/4/12.
 */

public class RxUtil {
    private static volatile RxUtil instance = null;
    private static List<Disposable> disposables;
    private static Context sContext;


    private RxUtil(){
        disposables = new ArrayList<>();
        sContext = FrameworkApplication.getContext();
    }
    public static RxUtil getInstance() {
        if (instance == null) {
            synchronized (RxUtil.class) {
                if (instance == null){
                    instance = new RxUtil();
                }
            }
        }
        return instance;
    }


    public static Context getContext(){
        checkInitialize();
        return sContext;
    }




    private static void checkInitialize(){
        if (sContext == null){
            throw new ExceptionInInitializerError("请先在全局Application中调用 RxHttpUtils.init() 初始化！");
        }
    }


    public void addDisposable(Disposable d){
        if (disposables != null && !disposables.contains(d)){
            disposables.add(d);
        }
    }

    public void removeDisposable(Disposable d){
        if (d != null && !d.isDisposed()){
            d.dispose();
        }
    }

    /**
     * 取消所有请求
     */
    public static void cancelAllRequest() {
        if (disposables != null) {
            for (Disposable disposable : disposables) {
                disposable.dispose();
            }
            disposables.clear();
        }
    }
}
