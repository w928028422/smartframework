package org.smart.framework.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyThreadLocal<T> {

    private Map<Thread, T> container = new ConcurrentHashMap<>();

    public void set(T value){
        container.put(Thread.currentThread(), value);
    }

    public T get(){
        Thread thread = Thread.currentThread();
        T value = container.get(thread);
        if (value == null && !container.containsKey(thread)){
            value = initialValue();
            container.put(thread, value);
        }
        return value;
    }

    public T initialValue() {
        return null;
    }

    public void remove(){
        container.remove(Thread.currentThread());
    }
}
