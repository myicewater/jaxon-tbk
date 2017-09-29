package com.jaxon.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TFUtil {

    /**
     * null or ""
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 非 （null or ""）
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * null or "" or " "
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    /**
     * 非（null or "" or " "）
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 集合为 null 或没有元素
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection collection){
        return collection == null || collection.size() == 0;
    }

    /**
     * 非（集合为 null 或没有元素）
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection collection){
        return !isEmpty(collection);
    }

    public static void main(String[] args) {
        List l = new ArrayList(2);
        l.add("2");
        System.out.println(isEmpty(l));
    }

}
