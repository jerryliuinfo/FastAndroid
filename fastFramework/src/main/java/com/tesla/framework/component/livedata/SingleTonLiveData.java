package com.tesla.framework.component.livedata;

import androidx.lifecycle.LiveData;

/**
 * Created by Jerry on 2020/12/31.
 */
public class SingleTonLiveData<T> extends LiveData<T> {
    private static SingleTonLiveData sInstance;
    private SingleTonLiveData(){

    }
    @Override
    public void postValue(T value) {
        super.postValue(value);
    }

    @Override
    public void setValue(T value) {
        super.setValue(value);
    }

    public static SingleTonLiveData getInstance(){
        if (sInstance == null){
            sInstance = new SingleTonLiveData();
        }
        return sInstance;
    }
}
