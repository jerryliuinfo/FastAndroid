package com.tesla.framework.network.cache;

import com.tesla.framework.common.setting.Setting;
import com.tesla.framework.common.util.KeyGenerator;
import com.tesla.framework.common.util.Logger;
import com.tesla.framework.component.orm.extra.Extra;
import com.tesla.framework.network.biz.ABizLogic;
import com.tesla.framework.network.biz.IResult;
import com.tesla.framework.network.http.Params;
import com.tesla.framework.support.bean.HomeBean;
import com.tesla.framework.support.bean.HomeBeans;
import com.tesla.framework.support.db.TeslaDB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerryliu on 2017/4/8.
 */

public class HomeCacheUtility implements ICacheUtility {
    @Override
    public void addCache(Setting action, Params params, IResult iResult) {
        Extra extra = new Extra(null,getCacheKey(action,params));
        HomeBeans homeBeans = (HomeBeans) iResult;
        if (homeBeans.beans != null && homeBeans.beans.size() > 0){
            long startTime = System.currentTimeMillis();
            TeslaDB.getDB().insert(extra, homeBeans.beans);
            Logger.d(ABizLogic.TAG, "HomeCacheUtility addCache cost time: %s ms", System.currentTimeMillis() - startTime);
        }
    }


    private String getCacheKey(Setting action, Params params){
        return KeyGenerator.generateMD5(action.getValue() +":"+params.getParameter("index"));
    }


    @Override
    public IResult findCacheData(Setting action, Params params) {
        Extra extra = new Extra(null,null);
        long startTime = System.currentTimeMillis();
        List<HomeBean> list = TeslaDB.getDB().select(extra, HomeBean.class);
        Logger.w(ABizLogic.TAG, String.format("HomeCacheUtility find cache const time: %s ms", String.valueOf(System.currentTimeMillis() - startTime)));
        Logger.d(ABizLogic.TAG, "HomeCacheUtility findCacheData key =  %s", extra.getKey());
        if (list != null && list.size() > 0){
            HomeBeans result = new HomeBeans();
            result.beans = (ArrayList<HomeBean>) list;
            return result;
        }


        return null;
    }
}
