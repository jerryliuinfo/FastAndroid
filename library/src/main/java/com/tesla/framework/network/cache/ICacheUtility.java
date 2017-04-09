package com.tesla.framework.network.cache;

import com.tesla.framework.common.setting.Setting;
import com.tesla.framework.network.biz.IResult;
import com.tesla.framework.network.http.Params;

/**
 * Created by jerryliu on 2017/4/8.
 */

public interface ICacheUtility {
    /**
     * 保存缓存
     * @param action
     * @param params
     * @param iResult
     */
    void addCache(Setting action, Params params, IResult iResult);

    /**
     * 读取缓存
     * @param action
     * @param params
     * @return
     */
    IResult findCacheData(Setting action, Params params);

}
