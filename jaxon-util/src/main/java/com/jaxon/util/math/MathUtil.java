package com.jaxon.util.math;

import java.math.BigDecimal;

/**
 * Math工具类
 * 基础计算不做异常捕捉，放在业务逻辑里进行处理判断
 */
public class MathUtil {
    public MathUtil() {
    }

    /**
     * 对double的计算转化成BigDecimal计算
     * @param d1
     * @param d2
     * @return
     */
    public static Double add(Double d1, Double d2) {
        BigDecimal b1 = new BigDecimal(String.valueOf(d1));
        BigDecimal b2 = new BigDecimal(String.valueOf(d2));
        return b1.add(b2).doubleValue();
    }

    public static Double sub(Double d1, Double d2) {
        BigDecimal b1 = new BigDecimal(String.valueOf(d1));
        BigDecimal b2 = new BigDecimal(String.valueOf(d2));
        return b1.subtract(b2).doubleValue();
    }

    public static Double mul(Double d1, Double d2) {
        BigDecimal b1 = new BigDecimal(String.valueOf(d1));
        BigDecimal b2 = new BigDecimal(String.valueOf(d2));
        return b1.multiply(b2).doubleValue();
    }

    public static Double div(Double d1, Double d2, int len) {
        BigDecimal b1 = new BigDecimal(String.valueOf(d1));
        BigDecimal b2 = new BigDecimal(String.valueOf(d2));
        return b1.divide(b2, len, 4).doubleValue();
    }

    public static Double round(Double d, int len) {
        BigDecimal b1 = new BigDecimal(d.doubleValue());
        BigDecimal b2 = new BigDecimal(1);
        return b1.divide(b2, len, 4).doubleValue();
    }

    public static void main(String[] args) {
        System.out.println(add(3.4,2.5));
        System.out.println(round(4.345,2));
    }
}
