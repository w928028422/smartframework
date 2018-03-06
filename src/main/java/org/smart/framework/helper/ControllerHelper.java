package org.smart.framework.helper;

import org.apache.commons.lang3.ArrayUtils;
import org.smart.framework.annotation.RequestMapping;
import org.smart.framework.bean.Handler;
import org.smart.framework.bean.Request;
import org.smart.framework.util.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器助手
 */
public final class ControllerHelper {
    /**
     * 存放请求与处理器的映射关系
     */
    private static final Map<Request, Handler> HANDLER_MAP = new HashMap<>();

    static {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if(CollectionUtil.isNotEmpty(controllerClassSet)){
            for (Class<?> controllerClass : controllerClassSet){
                Method[] methods = controllerClass.getDeclaredMethods();
                if(ArrayUtils.isNotEmpty(methods)){
                    for (Method method : methods){
                        RequestMapping action = method.getAnnotation(RequestMapping.class);
                        String mapping = action.value();
                        //验证Url映射的规则
                        if(mapping.matches("\\w+:/\\w*")){
                            String[] array = mapping.split(":");
                            if(ArrayUtils.isNotEmpty(array) && array.length == 2){
                                String requestMethod = array[0];
                                String requestPath = array[1];
                                Request request = new Request(requestMethod, requestPath);
                                Handler handler = new Handler(controllerClass, method);
                                HANDLER_MAP.put(request, handler);
                                System.out.println(HANDLER_MAP);
                            }
                        }
                    }
                }
            }
        }
    }

    public static Handler getHandler(String requestMethod, String requestPath){
        Request request = new Request(requestMethod, requestPath);
        return HANDLER_MAP.get(request);
    }

}
