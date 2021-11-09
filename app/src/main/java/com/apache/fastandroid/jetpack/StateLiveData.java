package com.apache.fastandroid.jetpack;


import com.tesla.framework.support.KidsException;

import androidx.lifecycle.MutableLiveData;

/**
 * Created by Jerry on 2021/5/26.
 */
public class StateLiveData<T > extends MutableLiveData<StateData<T>> {

    /**
     * Use this to put the Data on a LOADING Status
     */
   /* public void postLoading() {
        postValue(new StateData<T>().loading());
    }*/

    /**
     * Use this to put the Data on a ERROR DataStatus
     * @param throwable the error to be handled
     */
    public void postError(KidsException throwable) {
        postValue(new StateData<T>().error(throwable));
    }

    /**
     * Use this to put the Data on a ERROR DataStatus
     */
    public void postEmpty() {
        postValue(new StateData<T>().empty());
    }

    /**
     * Use this to put the Data on a SUCCESS DataStatus
     * @param data
     */
    public void postSuccess(T data) {
        postValue(new StateData<T>().success(data));
    }

    /**
     * 不要用这个，会覆盖掉postSuccess 中的data数据
     * Use this to put the Data on a COMPLETE DataStatus
     */
    /*public void postComplete() {
        StateData<T> value = getValue();
        value.complete();
        postValue(value);
    }*/

}
