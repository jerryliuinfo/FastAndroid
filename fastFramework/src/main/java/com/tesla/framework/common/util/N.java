package com.tesla.framework.common.util;

import java.util.List;
import java.util.Map;

/**
 * @author 01379852
 * 主要用于非空判断，因为使用频率太高，为了缩减代码，同时代表泛型的意思，取了NULL首字母N作为类名
 **/
public class N {

    /**  注意：支持传可变参数的方法只适用于(a ==null || b == null)，如果是(a==null && b==null)则不适用，需要分开判断 **/
    /**  注意：支持传可变参数的方法只适用于(a ==null || b == null)，如果是(a==null && b==null)则不适用，需要分开判断 **/
    /**  注意：支持传可变参数的方法只适用于(a ==null || b == null)，如果是(a==null && b==null)则不适用，需要分开判断 **/

    /**
     * 判断List是否为空
     */
    public static boolean isEmpty(List list) {
        return (null == list || list.isEmpty());
    }

    public static boolean isEmpty(Map map) {
        return (null == map || map.isEmpty());
    }

    /**
     * 判断2个字符串是否相等
     */
    public static boolean isEqual(String str1, String str2) {
        //丰小哥的服务器会返回"null" "" 这几种空字符串，代表的意思都是空，当2个值都是空时，直接给true，因为如果用null.equals进行比对，会产生crash，这里要注意
        if (isEmpty(str1) && isEmpty(str2)) {
            return true;
        }
        return null != str1 && str1.equals(str2);
    }

    public static boolean equal(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }

    /**
     * 判断多个字符串是否为空
     */
    public static boolean isEmpty(String... args) {
        for (String arg : args) {
            if (isEmpty(arg)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断多个对象是否为空，只要有一个为空则为true
     */
    public static boolean isEmpty(Object... objs) {
        for (Object obj : objs) {
            if (isEmpty(obj)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断单个对象是否为空
     */
    public static boolean isEmpty(Object obj) {
        return (null == obj || "".equals(obj));
    }


    public static boolean isNotEmtpy(String str) {
        return (str != null && !"".equalsIgnoreCase(str.trim()) && !"null".equalsIgnoreCase(str));
    }

}
