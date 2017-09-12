package com.yangqugame.annotation;

import com.yangqugame.message.DefaultService;

import java.lang.annotation.*;

/**
 * 协议注解
 * Created by Administrator on 2017/9/5 0005.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Proto {

    /**
     * @return 协议编号
     */
    public int code() default 0;

    /**
     * @return 对应的 protocol buffer message 类
     */
    public Class message();

    /**
     * @return 对应的处理类
     */
    public Class service() default DefaultService.class;

    public String method() default "handler";
}
