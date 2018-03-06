package org.smart.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart.framework.annotation.Aspect;
import org.smart.framework.annotation.Service;
import org.smart.framework.proxy.AspectProxy;
import org.smart.framework.proxy.Proxy;
import org.smart.framework.proxy.ProxyManager;
import org.smart.framework.proxy.TransactionProxy;

import java.lang.annotation.Annotation;
import java.util.*;

public final class AopHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);

    static {
        try{
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<Proxy>> entry : targetMap.entrySet()){
                Class<?> targetClass = entry.getKey();
                List<Proxy> proxyList = entry.getValue();
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                BeanHelper.setBean(targetClass, proxy);
            }
        }catch (Throwable e){
            LOGGER.error("aop异常", e);
        }
    }

    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Throwable{
        Set<Class<?>> targetClassSet = new HashSet<>();
        Class<? extends Annotation> annotation = aspect.value();
        if (annotation != null && !annotation.equals(Aspect.class)){
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }

    private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Throwable{
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);
        return  proxyMap;
    }

    private static void addTransactionProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Set<Class<?>> serviceClassSet = ClassHelper.getClassSetByAnnotation(Service.class);
        proxyMap.put(TransactionProxy.class, serviceClassSet);
    }

    private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap) throws Throwable{
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for (Class<?> proxyClass : proxyClassSet){
            if(proxyClass.isAnnotationPresent(Aspect.class)){
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        }
    }

    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Throwable{
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
        for (Map.Entry<Class<?>, Set<Class<?>>> entry : proxyMap.entrySet()){
            Class<?> proxyCLass = entry.getKey();
            Set<Class<?>> targetCLassSet = entry.getValue();
            for (Class<?> targetClass : targetCLassSet){
                Proxy proxy = (Proxy) proxyCLass.newInstance();
                if (!targetMap.containsKey(targetClass)){
                    targetMap.get(targetClass).add(proxy);
                }else{
                    List<Proxy> proxyList = new ArrayList<>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass, proxyList);
                }
            }
        }
        return targetMap;
    }
}
