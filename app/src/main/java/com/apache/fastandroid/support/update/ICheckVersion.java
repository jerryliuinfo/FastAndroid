package com.apache.fastandroid.support.update;

import com.apache.fastandroid.support.sdk.bean.UpdateBean;
import com.tesla.framework.network.task.TaskException;

/**
 * Created by 01370340 on 2017/12/24.
 */

public interface ICheckVersion {

    void onCheckPrepare();

    void onNewVersion(UpdateBean updateBean);

    void onNoNewVersion();

    void onCheckFailed(TaskException exception);

    void onCheckFinished();
}
