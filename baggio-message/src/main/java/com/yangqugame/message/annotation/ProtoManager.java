package com.yangqugame.message.annotation;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Administrator on 2017/9/5 0005.
 */
public class ProtoManager {

    private static Map<Integer, Proto> code2ProtoMap = new HashMap<>();  // key:协议号, value:协议信息
    private static Map<Class, Proto> bean2ProtoMap = new HashMap<>();  // key:bean 类, value:协议信息
    private static Map<Class, Class> message2beanMap = new HashMap<>();  // key:Protocol Buffer 类, value:bean 类
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

    public static Class getBeanClassByMessageClass(Class msgClass) {
        if (message2beanMap.containsKey(msgClass)) {
            return message2beanMap.get(msgClass);
        }
        return null;
    }

    public static String info() {
        Set<Integer> set = code2ProtoMap.keySet();
        List<Integer> list = new ArrayList<>(set);
        list.sort((o1, o2) -> o1 - o2);
        String info = "";
        for (Integer i : list) {
            Proto p = code2ProtoMap.get(i);
            info += String.format("code:%d, message:%s, service:%s, method:%s\n", p.code(), p.message().getSimpleName(), p.service().getSimpleName(), p.method());
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
                message2beanMap.put(proto.message(), cls);
                code2Bean.put(proto.code(), cls);
            }
        }
    }

    public static List<Object> message2ObjectList(Object o) {
        try {
            if (o instanceof List) {
                List<Object> beanList = new ArrayList<>();
                Class listCls = List.class;
                Method sizeMethod = listCls.getMethod("size");
                Method getMethod = listCls.getMethod("get", int.class);
                int size = (int) sizeMethod.invoke(o);
                for (int i=0; i<size; i++) {
                    Object sub = getMethod.invoke(o, i);
                    Class beanCls = getBeanClassByMessageClass(sub.getClass());
                    if (null != beanCls) {
                        Object bean = message2Bean((Message) sub, beanCls);
                        beanList.add(bean);
                    }
                }
                return beanList;
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Message> bean2MessageList(Object bean) {
        try {
            if (bean instanceof List) {
                List<Message> msgList = new ArrayList<>();
                Class listCls = List.class;
                Method sizeMethod = listCls.getMethod("size");
                Method getMethod = listCls.getMethod("get", int.class);
                int size = (int) sizeMethod.invoke(bean);
                for (int i=0; i<size; i++) {
                    Object sub = getMethod.invoke(bean, i);
                    Message subMsg = bean2Message(sub);
                    msgList.add(subMsg);
                }
                return msgList;
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  针对几种 java 类型，对 pb 值进行特殊转换
     * @param bean pb 类里面的属性值
     * @param field java 类属性类型
     * @return
     */
    private static Object castMessage2BeanType(Object bean, Field field) {
        Class<?> beanType = bean.getClass();
        Class<?> fieldType = field.getType();
        if (fieldType.equals(Short.class) || fieldType.equals(short.class)) {
            if (beanType.equals(Integer.class)) {
                return ((Integer) bean).shortValue();
            } else if (beanType.equals(Long.class)) {
                return ((Long) bean).shortValue();
            }
        } else if (fieldType.equals(Byte.class) || fieldType.equals(byte.class)) {
            if (beanType.equals(Integer.class)) {
                return ((Integer) bean).byteValue();
            } else if (beanType.equals(Long.class)) {
                return ((Long) bean).byteValue();
            }
        } else {
            if (fieldType.equals(Timestamp.class)) {
                return new Timestamp((Long) bean);
            } else {
                return bean;
            }
        }
        return bean;
    }

    /**
     *  针对几种 java 类型，对 java 值进行特殊转换
     * @param bean java bean
     * @param field java 类属性类型
     * @return
     */
    private static Object castBeanType2Message(Object bean, Field field) {
        Class<?> fieldType = field.getType();
        if (fieldType.equals(Byte.class) || fieldType.equals(byte.class)) {
            return Byte.toUnsignedInt((Byte) bean);
        }else if (fieldType.equals(Timestamp.class)) {
            return ((Timestamp) bean).getTime();
        } else {
            return bean;
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
                    v = castBeanType2Message(v, field);
                    if (null != v) {
                        if (d.getType() == Descriptors.FieldDescriptor.Type.MESSAGE) {
                            if (d.isRepeated()) {
                                List<Message> ml = bean2MessageList(v);
                                builder.setField(d, ml);
                            } else {
                                Message m = bean2Message(v);
                                builder.setField(d, m);
                            }
                        } else {
                            builder.setField(d, v);
                        }
                    }
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

    public static Object message2Bean(Message message, Class<?> cla) {
        try {
            Map<?, ?> map = message.getAllFields();
            Object bean = cla.newInstance();
            for(Object key : map.keySet()) {
                Descriptors.FieldDescriptor f = (Descriptors.FieldDescriptor) key;
                Field fd = cla.getDeclaredField(f.getName());
                fd.setAccessible(true);
                if (f.getType() == Descriptors.FieldDescriptor.Type.MESSAGE) {
                    if (f.isRepeated()) {
                        List l = message2ObjectList(map.get(key));
                        fd.set(bean, l);
                    } else {
                        Class beanCls = getBeanClassByMessageClass(map.get(key).getClass());
                        Object bo = message2Bean((Message) map.get(key), beanCls);
                        fd.set(bean, bo);
                    }
                } else {
                    if (null != fd) {
                        String name = ((Descriptors.FieldDescriptor) key).getName();
                        Field field = cla.getDeclaredField(name);
                        fd.set(bean, castMessage2BeanType(map.get(key), field));
                    }
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

    public static Class getBeanClass(int code) {
        if (code2Bean.containsKey(code)) {
            return code2Bean.get(code);
        }
        return null;
    }

    public static String getHandlerMethod(int code) {
        if (code2ProtoMap.containsKey(code)) {
            return code2ProtoMap.get(code).method();
        }
        return null;
    }

    public static void main(String [] argv) {
        scan("com");
        System.out.println(info());
    }
}
