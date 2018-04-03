package com.tesla.framework.common.util;

import java.util.Collection;
import java.util.Map;

/**
 * 集合工具类
 */

public class CollectionUtils {

    private CollectionUtils(){
        //留空
    }

    /**
     * 判断集合是否为空
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection collection){
        return collection==null || collection.isEmpty();
    }

    /**
     * 判断集合是否为空
     * @param collection
     * @return
     */
    public static boolean isEmpty(Map collection){
        return collection==null || collection.isEmpty();
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }
}
