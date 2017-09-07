package com.yangqugame.annotation;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by Administrator on 2017/9/5 0005.
 */
public class ProtoManager {

    private static Map<Integer, Proto> code2ProtoMap = new HashMap<>();  // key:协议号, value:协议信息
    private static Map<Class, Proto> bean2ProtoMap = new HashMap<>();  // key:bean 类, value:协议信息
    private static Map<Integer, Class> code2Bean = new HashMap<>();  // key:协议号, value:bean 类

    public static boolean existBeanProto(Class beanClass) {
        return bean2ProtoMap.containsKey(beanClass);
    }

    public static int getCodeByBean(Class beanClass) {
        if (bean2ProtoMap.containsKey(beanClass)) {
            return bean2ProtoMap.get(beanClass).code();
        }
        return -1;
    }

    public static String info() {
        Set<Integer> set = code2ProtoMap.keySet();
        List<Integer> list = new ArrayList<>(set);
        list.sort((o1, o2) -> o1 - o2);
        String info = "";
        for (Integer i : list) {
            Proto p = code2ProtoMap.get(i);
            info += String.format("code:%d, message:%s, service:%s\n", p.code(), p.message().getSimpleName(), p.service().getSimpleName());
        }
        return info;
    }

    public static void scan(String path) {
        Reflections reflections = new Reflections(path);
        Set<Class<?>> set = reflections.getTypesAnnotatedWith(Proto.class);
        for (Class<?> cls : set) {
            Proto proto = cls.getAnnotation(Proto.class);
            if (null != proto) {
                code2ProtoMap.put(proto.code(), proto);
                bean2ProtoMap.put(cls, proto);
                code2Bean.put(proto.code(), cls);
            }
        }
    }

    public static Message bean2Message(Object bean) {
        try {
            Class beanCls = bean.getClass();
            if (bean2ProtoMap.containsKey(beanCls)) {
                Class cls = bean2ProtoMap.get(beanCls).message();
                Method newBuilder = cls.getMethod("newBuilder");
                Message.Builder builder = (Message.Builder) newBuilder.invoke(null);

                Method getDescriptor = cls.getMethod("getDescriptor");
                Descriptors.Descriptor descriptor = (Descriptors.Descriptor) getDescriptor.invoke(cls);
                List<Descriptors.FieldDescriptor> list = descriptor.getFields();
                for (Descriptors.FieldDescriptor d : list) {
                    Field field = beanCls.getDeclaredField(d.getName());
                    field.setAccessible(true);
                    Object v = field.get(bean);
                    builder.setField(d, v);
                }
                return builder.build();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object byte2Message(byte[] data, int code) {
        try {
            if (code2ProtoMap.containsKey(code)) {
                Proto proto = code2ProtoMap.get(code);
                Class<?> cls = proto.message();
                Method newBuilderMethod = cls.getMethod("newBuilder");
                Object msgIns = newBuilderMethod.invoke(null);
                Method method = cls.getMethod("parseFrom", byte[].class);
                Message message = (Message) method.invoke(msgIns, data);
                return message2Bean(message, code2Bean.get(code));
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Object message2Bean(Message message, Class<?> cla) {
        try {
            Map<?, ?> map = message.getAllFields();
            Object bean = cla.newInstance();
            for(Object key : map.keySet()) {
                Descriptors.FieldDescriptor f = (Descriptors.FieldDescriptor) key;
                Field fd = cla.getDeclaredField(f.getName());
                if (null != fd) {
                    fd.setAccessible(true);
                    fd.set(bean, map.get(key));
                }
            }
            return bean;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class getHandlerClass(int code) {
        if (code2ProtoMap.containsKey(code)) {
            return code2ProtoMap.get(code).service();
        }
        return null;
    }

    public static void main(String [] argv) {
        scan("com");
        System.out.println(info());
    }
}
