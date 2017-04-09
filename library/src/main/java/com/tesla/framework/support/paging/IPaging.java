package com.tesla.framework.support.paging;

import java.io.Serializable;

/**
 * Created by jerryliu on 2017/3/31.
 */

public interface IPaging<T extends Serializable,Ts extends Serializable> extends Serializable{
    /**
     *
     * @return 上一页页码
     */
    String getPreviousPage();

    /**
     *
     * @return 下一页页码
     */

    String getNextPage();

    /**
     *
     * @param newDatas 新获取的数据结婚
     * @param firstData adapter中的第一条数据
     * @param lastData adapter中的最后一条数据
     */
    void processData(Ts newDatas, T firstData, T lastData);

}
