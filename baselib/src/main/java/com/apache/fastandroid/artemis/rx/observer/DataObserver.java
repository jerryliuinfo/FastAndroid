package com.apache.fastandroid.artemis.rx.observer;

import com.apache.fastandroid.artemis.exception.ApiException;
import com.apache.fastandroid.artemis.rx.RxUtil;
import com.apache.fastandroid.artemis.rx.base.BaseDataObserver;
import com.apache.fastandroid.artemis.support.bean.BaseBean;
import com.apache.fastandroid.artemis.support.exception.ICheck;
import com.tesla.framework.network.task.TaskException;

import io.reactivex.disposables.Disposable;

/**
 * Created by 01370340 on 2018/4/12.
 * 针对特定格式的时候设置的通用的Observer
 *         用户可以根据自己需求自定义自己的类继承BaseDataObserver<T>即可
 *         适用于
 *         {
 *         "code":200,
 *         "msg":"成功"
 *         "data":{
 *         "userName":"test"
 *         "token":"abcdefg123456789"
 *         "uid":"1"}
 *         }
 */

public abstract class DataObserver<T> extends BaseDataObserver<T> {
    @Override
    public void doOnSubscribe(Disposable d) {
        RxUtil.getInstance().addDisposable(d);
    }

    @Override
    public void doOnNext(BaseBean<T> data) {
        if (data == null){
            doOnError("数据为空");
            return;
        }
        if (data.getData() == null){
            doOnError("data字段为空");
            return;
        }
        //对服务器返回的数据做校验
        if ("0".equals(data.getCode())){
            T t = data.getData();
            if (t instanceof ICheck){
                try {
                    ((ICheck) t).check();
                    onSuccess(t);
                } catch (TaskException e) {
                    e.printStackTrace();
                    doOnError(e.getMessage());
                }
            }

        }else {
            doOnError(ApiException.getErrorMsgByCode(Integer.parseInt(data.getCode())));
        }
    }

    @Override
    public void doOnError(String msg) {
        onErrored(msg);
    }


    /**
     * 失败回调
     *
     * @param errorMsg 错误信息
     */
    protected abstract void onErrored(String errorMsg);

    /**
     * 成功回调
     *
     * @param data 结果
     */
    protected abstract void onSuccess(T data);


}
