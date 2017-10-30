package com.yangqugame.comm.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * properties 配置文件读取到类
 * Created by phiau on 2017/10/27 0027.
 */
public class PropertiesConfigUtil {

    public static Object file2ClassInstance(Properties prop, Class cla) throws IllegalAccessException, InstantiationException {
        Field[] fs = cla.getDeclaredFields();
        Object bean = cla.newInstance();
        if (null != fs) {
            for (Field fd : fs) {
                fd.setAccessible(true);
                String typeName = fd.getType().getName();
                if (-1 != typeName.indexOf("int")) {
                    fd.set(bean, Integer.valueOf(prop.getProperty(fd.getName())));
                } else if (-1 != typeName.indexOf("boolean")) {
                    fd.set(bean, Boolean.valueOf(prop.getProperty(fd.getName())));
                } else if (-1 != typeName.indexOf("short")){
                    fd.set(bean, Short.valueOf(prop.getProperty(fd.getName())));
                } else if (-1 != typeName.indexOf("byte")){
                    fd.set(bean, Byte.valueOf(prop.getProperty(fd.getName())));
                } else if (-1 != typeName.indexOf("long")){
                    fd.set(bean, Long.valueOf(prop.getProperty(fd.getName())));
                } else {
                    fd.set(bean, prop.getProperty(fd.getName()));
                }
            }
        }
        return bean;
    }

    public static Object file2ClassInstance(String filePath, Class cla) {
        Properties prop = new Properties();
        InputStream input = null;
        Object result = null;
        try {
            input = new FileInputStream(filePath);
            prop.load(input);
            result = file2ClassInstance(prop, cla);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != input) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }
    }
}
