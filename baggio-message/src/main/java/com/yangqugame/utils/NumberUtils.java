package com.yangqugame.utils;

import java.util.Random;

/**
 * Created by phiau on 2017/9/12 0012.
 */
public class NumberUtils {

    public static int getRandNum(int size) {
        Random random = new Random();
        return random.nextInt(size);
    }
}
