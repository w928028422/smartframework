package org.smart.framework;

import org.smart.framework.helper.*;
import org.smart.framework.util.ClassUtil;

public class HelperLoader {

    public static void init(){
        Class<?>[] classList = new Class[]{
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> clazz : classList){
            ClassUtil.loadClass(clazz.getName(), true);
        }
    }

}
