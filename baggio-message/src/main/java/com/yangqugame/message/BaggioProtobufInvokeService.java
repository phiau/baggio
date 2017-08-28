package com.yangqugame.message;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;
import com.yangqugame.msgBean.ReqLogin;
import jazmin.log.Logger;
import jazmin.log.LoggerFactory;
import jazmin.server.protobuf.Context;
import jazmin.server.protobuf.ProtobufInvokeService;
import jazmin.server.protobuf.ProtobufMessage;
import x1.proto.pb.Login;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by ging on 27/08/2017.
 * baggio
 */

public class BaggioProtobufInvokeService implements ProtobufInvokeService {

    private static Logger logger = LoggerFactory.getLogger(BaggioProtobufInvokeService.class);

    private static Map<Integer, String>  services = new HashMap<>();
    private static Map<Integer, Class> messageMap = new HashMap<>();
    private static Map<Class, Class> messageBeanMap = new HashMap<>();

    // 注册protobuf 服务
    static {
        services.put(10001, "com.yangqugame.message.LoginService");
    }

    static {
        messageMap.put(10001, Login.ReqLoginMessage.class);
        messageBeanMap.put(Login.ReqLoginMessage.class, ReqLogin.class);
    }

    public Message byte2Message(byte[] data, Class<?> c) {
        try {
            Method newBuilderMethod = c.getMethod("newBuilder");
            Object msgIns = newBuilderMethod.invoke(null);
            Method method = c.getMethod("parseFrom", byte[].class);
            Message message = (Message) method.invoke(msgIns, data);
            return message;

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

    public Object message2Bean(Message message) {
        try {
            if (messageBeanMap.containsKey(message.getClass())) {
                Class<?> cla = messageBeanMap.get(message.getClass());
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
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void invokeService(Context context) {
        ProtobufMessage requestMessage = context.getRequestMessage();
        int id = requestMessage.getId();
        String className = services.get(id);
        if (messageMap.containsKey(id)) {
            Message message = byte2Message(requestMessage.getData(), messageMap.get(id));
            if (null != message) {
                Object bean = message2Bean(message);
                logger.debug("从客户端接收到的消息为:" + bean);
            }
        }
        try {
            MessageService service = (MessageService) Class.forName(className).newInstance();
            service.invoke(context);
        } catch (Exception e) {
            logger.catching(e);
        }

    }
}
