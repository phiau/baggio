package com.yangqugame.comm.annotation;

import com.yangqugame.comm.annotation.entry.CacheAnnotation;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 缓存注解管理
 * Created by Administrator on 2017/9/4 0004.
 */
public class CachaAnnotationManager {

    private static Map<Class, CacheAnnotation> cacheAnnotationMap = new HashMap<>();

    public static void scan(String pack) {
        Reflections reflections = new Reflections(pack);
        Set<Class<?>> set = reflections.getTypesAnnotatedWith(CacheAnnotation.class);
        for (Class<?> c : set) {
            CacheAnnotation annotation = c.getAnnotation(CacheAnnotation.class);
            cacheAnnotationMap.put(c, annotation);
        }
    }

    public static int getCacheExpire(Class cls) {
        if (cacheAnnotationMap.containsKey(cls)) {
            return cacheAnnotationMap.get(cls).expire();
        }
        return 0;
    }

    public static String getCacheKeyName(Class cls) {
        if (cacheAnnotationMap.containsKey(cls)) {
            return cacheAnnotationMap.get(cls).keyName();
        }
        return "unknown";
    }

    public static String getCacheKeyIdR(Object o) {
        CacheAnnotation annotation = o.getClass().getAnnotation(CacheAnnotation.class);
        if (null != annotation) {
            try {
                Field field = o.getClass().getDeclaredField(annotation.keyId());
                if (null != field) {
                    field.setAccessible(true);
                    return field.get(o).toString();
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return "unknown";
    }
}
