package com.tesla.framework.network.biz;

/**
 * Created by jerryliu on 2017/3/28.
 */

public interface IResult {
    boolean isOutOfData();

    boolean isFromCache();

    boolean endPaging();

    String[] pagingIndex();

}
