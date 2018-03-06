package org.smart.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ReflectionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 创建实例
     */
    public static Object newInstance(Class<?> clazz){
        Object instance;
        try {
            instance = clazz.newInstance();
        } catch (Exception e) {
            LOGGER.error("创建实例时发生异常", e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * 调用方法
     */
    public static Object invokeMethod(Object object, Method method, Object... args){
        Object result;
        try {
            method.setAccessible(true);
            result = method.invoke(object, args);
        } catch (Exception e) {
            LOGGER.error("调用方法时发生异常", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 设置字段的值
     */
    public static void setField(Object object, Field field, Object value){
        try{
            field.setAccessible(true);
            field.set(object, value);
        }catch (Exception e){
            LOGGER.error("设置字段时发生异常", e);
            throw new RuntimeException(e);
        }
    }
}
