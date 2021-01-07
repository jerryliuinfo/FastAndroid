package com.apache.fastandroid.topic.support.cache;

import com.apache.fastandroid.topic.support.bean.ImageBean;
import com.apache.fastandroid.topic.support.bean.ImageResultBeans;
import com.tesla.framework.common.setting.Setting;
import com.tesla.framework.common.util.log.FastLog;
import com.tesla.framework.network.biz.IResult;
import com.tesla.framework.network.cache.ICacheUtility;
import com.tesla.framework.network.http.Params;
import com.tesla.framework.support.db.FastAndroidDB;

import java.util.List;

import static com.tesla.framework.support.db.FastAndroidDB.getDB;

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
            FastLog.d(FastLog.TAG, "PicCacheUtility保存缓存耗时: %s ms", (System.currentTimeMillis() - startTime));
        }

    }

    @Override
    public IResult findCacheData(Setting action, Params params) {
        List<ImageBean> imageBeanList = FastAndroidDB.getDB().select(null, ImageBean.class);
        if (imageBeanList != null && imageBeanList.size() > 0){
            ImageResultBeans result = new ImageResultBeans();
            result.imgs = imageBeanList;
            return result;
        }
        return null;

    }
}
