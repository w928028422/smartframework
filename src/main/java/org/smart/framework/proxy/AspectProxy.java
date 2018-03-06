package org.smart.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 切面代理
 */
public abstract class AspectProxy implements Proxy{

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public final Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;

        Class<?> clazz = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();

        begin();
        try{
            if(intercept(clazz, method, params)){
                before(clazz, method, params);
                result = proxyChain.doProxyChain();
                after(clazz, method, params);
            }else{
                result = proxyChain.doProxyChain();
            }
        }catch (Exception e){
            LOGGER.error("切面代理时发生异常", e);
            error(clazz, method, params, e);
            throw e;
        }finally {
            end();
        }
        return result;
    }

    public void end() {
    }

    public void error(Class<?> clazz, Method method, Object[] params, Throwable e) {
    }

    public void after(Class<?> clazz, Method method, Object[] params) throws Throwable{
    }

    public void before(Class<?> clazz, Method method, Object[] params) throws Throwable{
    }

    public boolean intercept(Class<?> clazz, Method method, Object[] params) throws Throwable{
        return true;
    }

    public void begin(){
    }
}
