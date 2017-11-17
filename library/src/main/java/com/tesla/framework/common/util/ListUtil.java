package com.tesla.framework.common.util;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 01370340 on 2017/11/17.
 */

public class ListUtil {

    public static <T> List<T> arrayToList(T[] array){
        return Arrays.asList(array);
    }

    public static <T> T[] listToArray(List<T> list){
        return (T[]) list.toArray();
    }
}
