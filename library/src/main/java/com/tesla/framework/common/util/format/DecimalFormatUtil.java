package com.tesla.framework.common.util.format;

import java.text.DecimalFormat;

/**
 * Created by jerryliu on 2017/6/24.
 */

public class DecimalFormatUtil {
    /**
     * 取整数部分
     * @param value
     * @return
     */
    public static String format(double value){
        DecimalFormat decimalFormat = new DecimalFormat("#0");
        return decimalFormat.format(value);
    }

    public static String formatWithOneDecimal(double value){
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        return decimalFormat.format(value);
    }

    public static String formatWithTwoDecimal(double value){
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return decimalFormat.format(value);
    }

    public static void main(String[] args) {
        System.out.println(format(31.145));
        System.out.println(formatWithOneDecimal(31.145));
    }
}
