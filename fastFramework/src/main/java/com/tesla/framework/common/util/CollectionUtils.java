package com.tesla.framework.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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




    public static void printList(List<String> list){
        if (list != null && !list.isEmpty()){
            StringBuilder stringBuilder1 = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                stringBuilder1.append(list.get(i));
                if (i != list.size() -1){
                    stringBuilder1.append("_");
                }
            }
            System.out.println(stringBuilder1.toString());
        }
    }

    /**
     * 返回两个集合的交集,可以判断两个集合是否有交集（不修改源数据）
     * @param list1 {'aaa','bbb','ccc'}
     * @param list2  {'ddd','bbb','eee'}
     * @param <T>
     * @return  {bbb}
     */
    public static <T> List<T> getIntersection(List<T> list1, List<T> list2){
        if (list1 != null && list2 != null){
            List<T> tempList1 = new ArrayList<>(list1);
            List<T> tempList2 = new ArrayList<>(list2);
            tempList1.retainAll(tempList2);
            return tempList1;
        }
        return new ArrayList<>();
    }

    /**
     * 返回两个集合的交集,可以判断两个集合是否有交集（会修改源数据）
     * @param list1
     * @param list2
     * @param <T>
     * @return
     */
    public static <T> List<T> getIntersectionModify(List<T> list1, List<T> list2){
        if (list1 != null){
            list1.retainAll(list2);
        }
        return list1;
    }

    /**
     * 判断两个集合是否有交集
      * @return
     */
    public static <T> boolean haveIntersection(List<T> list1, List<T> list2){
        return Collections.disjoint(list1,list2);
    }













}
