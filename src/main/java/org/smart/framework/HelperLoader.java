package org.smart.framework;

import org.smart.framework.helper.BeanHelper;
import org.smart.framework.helper.ClassHelper;
import org.smart.framework.helper.ControllerHelper;
import org.smart.framework.helper.IocHelper;
import org.smart.framework.util.ClassUtil;

public class HelperLoader {

    public static void init(){
        Class<?>[] classList = new Class[]{
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> clazz : classList){
            ClassUtil.loadClass(clazz.getName(), false);
        }
    }
}
