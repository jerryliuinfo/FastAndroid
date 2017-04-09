package com.tesla.framework.network.biz;

import java.io.Serializable;

/**
 * Created by jerryliu on 2017/3/28.
 */

public class ResultBean implements IResult,Serializable {
    private boolean outOfData;
    private boolean isFramCache;
    private boolean endPaging;
    private String[]pagingIndex;
    @Override
    public boolean isOutOfData() {
        return outOfData;
    }

    @Override
    public boolean isFromCache() {
        return isFramCache;
    }

    @Override
    public boolean endPaging() {
        return endPaging;
    }

    @Override
    public String[] pagingIndex() {
        return pagingIndex;
    }

    public void setOutOfData(boolean outOfData) {
        this.outOfData = outOfData;
    }

    public boolean isFramCache() {
        return isFramCache;
    }

    public void setFramCache(boolean framCache) {
        isFramCache = framCache;
    }

    public boolean isEndPaging() {
        return endPaging;
    }

    public void setEndPaging(boolean endPaging) {
        this.endPaging = endPaging;
    }

    public String[] getPagingIndex() {
        return pagingIndex;
    }

    public void setPagingIndex(String[] pagingIndex) {
        this.pagingIndex = pagingIndex;
    }
}
