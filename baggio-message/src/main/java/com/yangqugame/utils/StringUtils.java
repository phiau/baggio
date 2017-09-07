package com.yangqugame.utils;

/**
 * Created by Administrator on 2017/9/7 0007.
 */
public class StringUtils {

    public static boolean isNullOrEmpty(String s) {
        if (null == s || 0 == s.length()) {
            return true;
        }
        return false;
    }
}
