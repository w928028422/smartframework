package org.smart.framework.helper;

import org.smart.framework.annotation.Controller;
import org.smart.framework.annotation.Service;
import org.smart.framework.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * 类助手
 */
public final class ClassHelper {
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取应用包名下的所有类
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取应用包名下的所有Service类
     */
    public static Set<Class<?>> getServiceClassSet(){
        Set<Class<?>> classSet = new HashSet<>();
        for(Class<?> clazz : CLASS_SET){
            if(clazz.isAnnotationPresent(Service.class)){
                classSet.add(clazz);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下的所有Controller类
     */
    public static Set<Class<?>> getControllerClassSet(){
        Set<Class<?>> classSet = new HashSet<>();
        for(Class<?> clazz : CLASS_SET){
            if(clazz.isAnnotationPresent(Controller.class)){
                classSet.add(clazz);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下的所有Bean类
     */
    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> beanClassSet = new HashSet<>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        return beanClassSet;
    }

    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass){
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : CLASS_SET){
            if (superClass.isAssignableFrom(clazz) && !clazz.equals(superClass)){
                classSet.add(clazz);
            }
        }
        return classSet;
    }

    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass){
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : CLASS_SET){
            if (clazz.isAnnotationPresent(annotationClass)){
                classSet.add(clazz);
            }
        }
        return classSet;
    }
}
