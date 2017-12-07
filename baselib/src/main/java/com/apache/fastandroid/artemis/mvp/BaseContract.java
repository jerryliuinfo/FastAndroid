package com.apache.fastandroid.artemis.mvp;

/**
 * Created by 01370340 on 2017/10/21.
 */

public interface BaseContract {

    interface BasePresenter<T>{

        void attachView(T view);

        void detachView();
    }


    interface BaseView{

        void onFailed(Throwable e);

        void onSuccess();

        void onFinished();

    }
}
