package org.smart.framework.helper;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.smart.framework.annotation.Inject;
import org.smart.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入的助手
 */
public final class IocHelper {

    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if(MapUtils.isNotEmpty(beanMap))
        for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()){
            Class<?> clazz = beanEntry.getKey();
            Object instance = beanEntry.getValue();
            Field[] fields = clazz.getDeclaredFields();
            if(ArrayUtils.isNotEmpty(fields)){
                for (Field field : fields){
                    if(field.isAnnotationPresent(Inject.class)){
                        //获取Bean的字段对应的实例
                        Class<?> fieldClass = field.getType();
                        Object fieldInstance = beanMap.get(fieldClass);
                        if(fieldInstance != null){
                            ReflectionUtil.setField(instance, field, fieldInstance);
                        }
                    }
                }
            }
        }
    }
}
