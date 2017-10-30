package com.yangqugame.comm.annotation.entry;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2017/9/4 0004.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheAnnotation {

    /**
     * @return 超时的时间，单位秒
     */
    int expire() default 0;

    /**
     * @return key 名
     */
    String keyName();

    /**
     * @return key id
     */
    String keyId();
}
