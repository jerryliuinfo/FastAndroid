package com.tesla.framework.support.paging.offset;

import com.tesla.framework.support.paging.IPaging;

import java.io.Serializable;

/**
 * Created by jerryliu on 2017/3/31.
 */

public class OffsetPaging<T extends Serializable, Ts extends OffsetResult> implements IPaging<T, Ts>{

    int offset;
    @Override
    public String getPreviousPage() {
        return "";
    }

    @Override
    public String getNextPage() {
        return String.valueOf(offset);
    }

    @Override
    public void processData(Ts newDatas, T firstData, T lastData) {
        offset = newDatas.getOffset();

    }
}
