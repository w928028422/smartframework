package org.smart.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart.framework.annotation.Transaction;
import org.smart.framework.helper.DatabaseHelper;

import java.lang.reflect.Method;

/**
 * 事务代理
 */
public class TransactionProxy implements Proxy{

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProxy.class);

    private static final ThreadLocal<Boolean> FLAG_HOLDER = ThreadLocal.withInitial(() -> false);

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result;
        boolean flag = FLAG_HOLDER.get();
        Method method = proxyChain.getTargetMethod();
        if (!flag && method.isAnnotationPresent(Transaction.class)){
            FLAG_HOLDER.set(true);
            try{
                DatabaseHelper.beginTransaction();;
                LOGGER.debug("开始事务...");
                result = proxyChain.doProxyChain();
                DatabaseHelper.commitTransaction();
                LOGGER.debug("提交事务...");
            }catch (Exception e){
                DatabaseHelper.rollbackTransaction();
                LOGGER.debug("回滚事务...");
                throw e;
            }finally {
                FLAG_HOLDER.remove();
            }
        }else{
            result = proxyChain.doProxyChain();
        }
        return result;
    }
}
