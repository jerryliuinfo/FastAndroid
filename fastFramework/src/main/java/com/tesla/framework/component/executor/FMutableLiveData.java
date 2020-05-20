package com.tesla.framework.component.executor;

/**
 * Created by Jerry on 2020-05-16.
 */
public class FMutableLiveData<T> extends FLiveData<T> {
    @Override
    public void postValue(T value) {
        super.postValue(value);
    }

    @Override
    public void setValue(T value) {
        super.setValue(value);
    }
}
