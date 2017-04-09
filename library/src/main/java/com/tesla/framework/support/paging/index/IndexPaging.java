package com.tesla.framework.support.paging.index;

import android.text.TextUtils;

import com.tesla.framework.network.biz.IResult;
import com.tesla.framework.support.paging.IPaging;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Created by jerryliu on 2017/3/31.
 */

public class IndexPaging<T extends Serializable,Ts extends Serializable> implements IPaging<T,Ts> {
    private int pageIndex;
    private int pageTotal;



    private String pageTotalField;//总的页码数

    @Override
    public String getPreviousPage() {
        return String.valueOf(pageIndex);
    }

    @Override
    public String getNextPage() {
        return String.valueOf(pageIndex);
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
        if (!TextUtils.isEmpty(pageTotalField)){
            Class clz = newDatas.getClass();
            while (clz != Object.class){
                try {
                    Field field = clz.getDeclaredField(pageTotalField);
                    field.setAccessible(true);
                    pageTotal = Integer.parseInt(field.get(newDatas).toString());
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    clz = clz.getSuperclass();
                }
            }

        }

    }
}
