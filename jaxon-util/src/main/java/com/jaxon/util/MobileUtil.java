package com.jaxon.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机号工具类
 */
public class MobileUtil {

    /**
     * 检测是否是手机号
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile){
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(mobile);
        b = m.matches();
        return b;
    }

    public static void main(String[] args) {
        System.out.println(isMobile("00000183116"));
    }
}
