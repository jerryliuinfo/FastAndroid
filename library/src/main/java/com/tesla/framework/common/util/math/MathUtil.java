package com.tesla.framework.common.util.math;

/**
 * Created by jerryliu on 2017/6/27.
 */

public class MathUtil {
    /**
     * 四舍五入保留一位小数
     * @param value
     * @return
     */
    public static float roundOne(float value) {
        return Math.round(value * 10) / 10.0f;
    }

    /**
     * 四舍五入保留二位小数
     * @param value
     * @return
     */
    public static float roundTwo(float value) {
        return Math.round(value * 100) / 100.0f;
    }

    /**
     * 四舍五入保留三位小数
     * @param value
     * @return
     */
    public static float roundThree(float value) {
        return Math.round(value * 100) / 100.0f;
    }

}
