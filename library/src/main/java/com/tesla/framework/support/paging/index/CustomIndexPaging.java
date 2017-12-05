package com.tesla.framework.support.paging.index;

import com.tesla.framework.network.biz.IResult;
import com.tesla.framework.support.paging.IPaging;

import java.io.Serializable;

/**
 * Created by jerryliu on 2017/3/31.
 */

public class CustomIndexPaging<T extends Serializable,Ts extends Serializable> implements IPaging<T,Ts> {
    private int pageIndex;
    private int pageTotal;

    public static final int DEF_PAGE_SIZE = 10;

    private int pageSize = DEF_PAGE_SIZE;

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String getPreviousPage() {
        return String.valueOf(pageIndex);
    }

    @Override
    public String getNextPage() {
        return String.valueOf(pageIndex * pageSize);
    }

    @Override
    public void processData(Ts newDatas, T firstData, T lastData) {
        pageIndex++;
        if (newDatas instanceof IResult){
            IResult iResult = (IResult) newDatas;
            if (iResult.isFromCache() && iResult.pagingIndex() != null){
                pageIndex = Integer.parseInt(iResult.pagingIndex()[1]);
            }
        }

    }
}
