package com.tesla.framework.common.util.math;

import java.math.BigDecimal;

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







    public static void main(String[] args) {
        double db1 = 1.03;
        double db2 = 0.42;
        //BigDecimal bigDecimal1 = new BigDecimal(1.03);//会有精度问题 1.0300000000000000514
        BigDecimal bigDecimal1 = BigDecimal.valueOf(1.03);
        BigDecimal bigDecimal2 = BigDecimal.valueOf(0.42);
        BigDecimal bigDecimal3 = new BigDecimal(Double.toString(1.03));
        System.out.println("bigDecimal1 - bigDecimal2 = "+ bigDecimal1.subtract(bigDecimal2).doubleValue()+", db1 - db2 = "+(db1 - db2) +", bigDecimal3 = "+bigDecimal3);
    }

}
