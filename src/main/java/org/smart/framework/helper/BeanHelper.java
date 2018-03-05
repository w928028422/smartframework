package org.smart.framework.helper;

import org.smart.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Bean助手
 */
public final class BeanHelper {
    /**
     * 存放Bean类型和Bean实例的映射关系
     */
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {
        Set<Class<?>> classSet = ClassHelper.getBeanClassSet();
        for(Class<?> clazz : classSet){
            Object instance = ReflectionUtil.newInstance(clazz);
            BEAN_MAP.put(clazz, instance);
        }
    }

    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * 获取Bean实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz){
        if(!BEAN_MAP.containsKey(clazz)){
            throw new RuntimeException("无法获取该类的Bean实例");
        }
        return (T) BEAN_MAP.get(clazz);
    }
}
