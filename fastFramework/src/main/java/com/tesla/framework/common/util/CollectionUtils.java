package com.tesla.framework.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断集合是否为空
     * @param collection
     * @return
     */
    public static boolean isEmpty(Map collection){
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }


    public static String listToString(List<String> list){
        StringBuilder stringBuilder1 = new StringBuilder();
        if (list != null && !list.isEmpty()){
            for (int i = 0, size = list.size(); i < size; i++) {
                stringBuilder1.append(list.get(i));
                if (i != list.size() -1){
                    stringBuilder1.append("_");
                }
            }
        }
        return stringBuilder1.toString();
    }


    /**
     * 返回一个只读的list，外部不能通过这个list去做修改操作，否则会throws java.lang.UnsupportedOperationException
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<T> immutableList(List<T> list){
        return Collections.unmodifiableList(list);
    }




    /** Returns an immutable list containing {@code elements}. */
    public static <T> List<T> immutableList(T... elements) {
        return Collections.unmodifiableList(Arrays.asList(elements.clone()));
    }

    /**
     * 返回两个集合的交集,可以判断两个集合是否有交集（不修改源数据）
     * @param list1 {'aaa','bbb','ccc'}
     * @param list2  {'ddd','bbb','eee'}
     * @param <T>
     * @return  {bbb}
     */
    public static <T> List<T> retailAll(List<T> list1, List<T> list2){
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
     * @param list1  {'aaa','bbb','ccc'}
     * @param list2 {'ddd','bbb','eee'}
     * @param <T> {bbb}
     * @return 返回后list1也变为了 {bbb}
     */
    public static <T> List<T> retailAllModify(List<T> list1, List<T> list2){
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



    /**
     * 返回两个数组中重合的元素
     *
     */
    @SuppressWarnings("unchecked")
    public static String[] intersect(
            Comparator<? super String> comparator, String[] first, String[] second) {
        List<String> result = new ArrayList<>();
        for (String a : first) {
            for (String b : second) {
                if (comparator.compare(a, b) == 0) {
                    result.add(a);
                    break;
                }
            }
        }
        return result.toArray(new String[result.size()]);
    }


    /**
     * 返回两个集合中重合的元素
     * @param comparator
     * @param first
     * @param second
     * @param <T>
     * @return
     */
    public static <T> List<T> intersectArray(
            Comparator<? super T> comparator, List<T> first, List<T> second) {
        List<T> result = new ArrayList<>();
        for (T a : first) {
            for (T b : second) {
                if (comparator.compare(a, b) == 0) {
                    result.add(a);
                    break;
                }
            }
        }
        return result;
    }



    public static String[] concat(String[] array, String value) {
        String[] result = new String[array.length + 1];
        System.arraycopy(array, 0, result, 0, array.length);
        result[result.length - 1] = value;
        return result;
    }





}
