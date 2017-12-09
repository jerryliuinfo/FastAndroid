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
        /**
         * 开始执行 可以弹出loading框之类业务操作
         */
        void onPrepare();

        void onFailed(Throwable e);

        void onFinished();

    }
}
