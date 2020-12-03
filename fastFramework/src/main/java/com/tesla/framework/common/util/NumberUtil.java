package com.tesla.framework.common.util;

import java.math.BigDecimal;

/**
 * Created by Jerry on 2020/12/3.
 */
public class NumberUtil {

    /**
     * Double类型保留指定位数的小数，返回double类型（四舍五入）
     * newScale 为指定的位数
     */
    private static double formatDouble(double d, int newScale) {
        BigDecimal bd = new BigDecimal(d);
        return bd.setScale(newScale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
