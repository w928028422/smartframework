package org.smart.framework.proxy;

/**
 * 代理借口
 */
public interface Proxy {

    /**
     *执行链式代理
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
