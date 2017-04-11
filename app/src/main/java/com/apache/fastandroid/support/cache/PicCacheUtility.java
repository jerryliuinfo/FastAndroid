package com.apache.fastandroid.support.cache;

import com.apache.fastandroid.support.bean.ImageBean;
import com.apache.fastandroid.support.bean.ImageResultBeans;
import com.tesla.framework.common.setting.Setting;
import com.tesla.framework.common.util.Logger;
import com.tesla.framework.network.biz.IResult;
import com.tesla.framework.network.cache.ICacheUtility;
import com.tesla.framework.network.http.Params;
import com.tesla.framework.support.db.TeslaDB;

import java.util.List;

import static com.tesla.framework.support.db.TeslaDB.getDB;

/**
 * Created by jerryliu on 2017/4/11.
 */

public class PicCacheUtility implements ICacheUtility {
    @Override
    public void addCache(Setting action, Params params, IResult iResult) {
        if (iResult instanceof ImageResultBeans){
            long startTime = System.currentTimeMillis();

            ImageResultBeans result = (ImageResultBeans) iResult;
            getDB().insert(null,result.imgs);
            Logger.d(Logger.TAG, "PicCacheUtility保存缓存耗时: %s ms", (System.currentTimeMillis()));
        }

    }

    @Override
    public IResult findCacheData(Setting action, Params params) {
        List<ImageBean> imageBeanList = TeslaDB.getDB().select(null, ImageBean.class);
        if (imageBeanList != null && imageBeanList.size() > 0){
            ImageResultBeans result = new ImageResultBeans();
            result.imgs = imageBeanList;
            return result;
        }
        return null;

    }
}
