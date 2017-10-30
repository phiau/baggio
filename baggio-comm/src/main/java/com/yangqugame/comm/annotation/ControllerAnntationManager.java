package com.yangqugame.comm.annotation;

import com.yangqugame.comm.annotation.entry.ControllerResAnnotation;
import com.yangqugame.comm.annotation.entry.RequestMappingAnnotation;
import org.apache.logging.log4j.util.Strings;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 处理请求类的注解管理
 * Created by Administrator on 2017/9/4 0004.
 */
public class ControllerAnntationManager {

    public static class ControllerItem {
        public Class cls;
        public Method m;
        public ControllerItem(Class cls, Method m) {
            this.cls = cls;
            this.m = m;
        }
    }

    private static Map<Class, Object> uriHandler = new HashMap<>();
    private static Map<String, ControllerItem> uriMap = new HashMap<>();  // key:请求路径, value:包含了类和方法

    private static String formatSubPath(String subPath) {
        if (!subPath.startsWith("/")) {
            subPath = "/" + subPath;
        }
        while (subPath.endsWith("/")) {
            subPath = subPath.substring(0, subPath.length() - 1);
        }
        return subPath;
    }

    private static void registerController(Class cls, Method m, String uri) {
        try {
            uri = formatSubPath(uri);
            uriMap.put(uri, new ControllerItem(cls, m));
            if (!uriHandler.containsKey(cls)) {
                uriHandler.put(cls, cls.newInstance());
            }
            System.out.println(String.format("[info]: uri:%s ---> class:%s.%s", uri, cls.getName(), m.getName()));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void scan(String pack) {
        Reflections reflections = new Reflections(pack);
        Set<Class<?>> set = reflections.getTypesAnnotatedWith(ControllerResAnnotation.class);
        for (Class cls : set) {
            Method[] ms = cls.getDeclaredMethods();
            for (Method m : ms) {
                RequestMappingAnnotation r = m.getAnnotation(RequestMappingAnnotation.class);
                if (null != r) {
                    String value = r.value();
                    if (Strings.isEmpty(value)) {
                        System.out.println("[error]: RequestMapping value is empty, class " + cls.getSimpleName());
                        return;
                    }
                    registerController(cls, m, value);
                }
            }
        }
    }

    public static ControllerItem getControllerItem(String subPath) {
        return uriMap.get(subPath);
    }

    public static Object getControllerObj(Class cls) {
        return uriHandler.get(cls);
    }
}
