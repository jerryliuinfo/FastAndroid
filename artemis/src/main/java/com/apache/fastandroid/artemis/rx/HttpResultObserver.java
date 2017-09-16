package com.apache.fastandroid.artemis.rx;

import com.apache.fastandroid.artemis.support.bean.BaseBean;
import com.apache.fastandroid.artemis.support.exception.ICheck;
import com.tesla.framework.network.task.TaskException;

import rx.Subscriber;

/**
 * Created by 01370340 on 2017/9/4.
 */

public abstract class HttpResultObserver<T> extends Subscriber<T> implements ICallback<T>{

    @Override
    public void onCompleted() {
        onFinished();
    }

    @Override
    public void onError(Throwable e) {
        onFailed(e);
    }

    @Override
    public void onNext(T result) {
        if (result == null){
            onFailed(new TaskException("数据为空"));
            return;
        }
        //检查code
        if (result instanceof BaseBean){
            BaseBean baseBean = (BaseBean) result;
            if (!"0".equals(baseBean.getCode())){
                TaskException taskException =  new TaskException(baseBean.getCode(),baseBean.getMsg());
                onFailed(taskException);
                return;
            }
        }

        //检查一些业务异常
        if (result instanceof ICheck){
            try {
                ((ICheck) result).check();
            } catch (TaskException e) {
                e.printStackTrace();
                onFailed(e);
                return;
            }
        }
        onSuccess(result);
    }


}
