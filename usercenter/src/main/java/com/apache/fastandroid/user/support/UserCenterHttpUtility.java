package com.apache.fastandroid.user.support;

import com.tesla.framework.common.setting.Setting;
import com.tesla.framework.network.http.HttpConfig;
import com.tesla.framework.network.http.IHttpUtility;
import com.tesla.framework.network.http.Params;
import com.tesla.framework.network.task.TaskException;

/**
 * Created by 01370340 on 2017/9/1.
 */

public class UserCenterHttpUtility implements IHttpUtility {

    @Override
    public <T> T doGet(HttpConfig config, Setting action, Params urlParams, Class<T> responseCls) throws TaskException {
        if ("doLogin".equals(action.getType())){



        }

        return null;
    }

    @Override
    public <T> T doPost(HttpConfig config, Setting action, Params urlParams, Params bodyParams, Object requestObj,
                        Class<T> responseCls) throws TaskException {
        return null;
    }

    @Override
    public <T> T doPostFiles(HttpConfig config, Setting action, Params urlParams, Params bodyParams, MultipartFile[] files, Class<T> responseCls) throws TaskException {
        return null;
    }
}
