package com.tesla.framework.support.paging.timeline;

import android.text.TextUtils;

import com.tesla.framework.support.paging.IPaging;

import java.io.Serializable;

/**
 * Created by jerryliu on 2017/3/31.
 */

public class TimeLinePaging<T extends TimelineResult,Ts extends Serializable> implements IPaging<T,Ts>{

    private String firstId;
    private String lastId;
    @Override
    public String getPreviousPage() {
        return firstId;
    }

    @Override
    public String getNextPage() {
        if (TextUtils.isEmpty(lastId)){
            return null;
        }
        return String.valueOf(Long.parseLong(lastId));
    }

    @Override
    public void processData(Ts newDatas, T firstData, T lastData) {
        if (firstData != null){
            firstId = firstData.getId();
        }
        if (lastData != null){
            lastId = lastData.getId();
        }

    }
}
