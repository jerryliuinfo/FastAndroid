package com.apache.fastandroid.jetpack;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by Jerry on 2021/5/25.
 * 继承这个BaseViewModel一定要用public修饰，否则会报 can't create class viewmodel 的错误
 */
public class BaseViewModel extends ViewModel {

    private MutableLiveData<Boolean> mShowLoading = new MutableLiveData<>(false);

    public void setLoading(boolean loading){
        mShowLoading.postValue(loading);
    }
    public MutableLiveData<Boolean> getLoading() {
        return mShowLoading;
    }


    public void showLoading(){
        mShowLoading.postValue(true);
    }

    public void dismissLoading(){
        mShowLoading.postValue(false);
    }
}
