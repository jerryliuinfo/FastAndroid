package com.apache.fastandroid.novel.support.cache;

import com.apache.fastandroid.novel.support.util.NovelLog;
import com.tesla.framework.common.setting.Setting;
import com.tesla.framework.network.biz.IResult;
import com.tesla.framework.network.cache.ICacheUtility;
import com.tesla.framework.network.http.Params;

/**
 * Created by 01370340 on 2017/11/6.
 */

public class RankListCacheUtility implements ICacheUtility {
    @Override
    public void addCache(Setting action, Params params, IResult iResult) {

    }

    @Override
    public IResult findCacheData(Setting action, Params params) {
        NovelLog.d("RankListCacheUtility findCacheData");
        return null;
    }
}
