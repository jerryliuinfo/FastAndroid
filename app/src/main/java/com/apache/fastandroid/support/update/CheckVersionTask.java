package com.apache.fastandroid.support.update;

import com.apache.fastandroid.support.sdk.Sdk;
import com.apache.fastandroid.support.sdk.bean.UpdateBean;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.network.task.WorkTask;

/**
 * Created by 01370340 on 2017/12/24.
 */

public class CheckVersionTask extends WorkTask<Void,Void,UpdateBean> {


    private ICheckVersion iCheckVersion;

    public void setCallback(ICheckVersion iCheckVersion) {
        this.iCheckVersion = iCheckVersion;
    }

    @Override
    protected void onPrepare() {
        super.onPrepare();
        iCheckVersion.onCheckPrepare();
    }

    @Override
    public UpdateBean workInBackground(Void... params) throws TaskException {
        return Sdk.newInstance().checkAppVersion();
    }

    @Override
    protected void onSuccess(UpdateBean updateBean) {
        super.onSuccess(updateBean);

        if (updateBean != null){
            iCheckVersion.onNewVersion(updateBean);
        }else {
            iCheckVersion.onNoNewVersion();
        }
    }

    @Override
    protected void onFailure(TaskException exception) {
        super.onFailure(exception);
        iCheckVersion.onCheckFailed(exception);
    }


    @Override
    protected void onFinished() {
        super.onFinished();
        iCheckVersion.onCheckFinished();
    }
}
