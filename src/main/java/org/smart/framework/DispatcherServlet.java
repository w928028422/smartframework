package org.smart.framework;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.smart.framework.bean.Data;
import org.smart.framework.bean.Handler;
import org.smart.framework.bean.Param;
import org.smart.framework.bean.View;
import org.smart.framework.helper.BeanHelper;
import org.smart.framework.helper.ConfigHelper;
import org.smart.framework.helper.ControllerHelper;
import org.smart.framework.util.CodecUtil;
import org.smart.framework.util.JsonUtil;
import org.smart.framework.util.ReflectionUtil;
import org.smart.framework.util.StreamUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求转发器
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet{

    @Override
    public void init(ServletConfig config) throws ServletException {
        HelperLoader.init();
        ServletContext servletContext = config.getServletContext();
        //注册处理jsp的servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        //注册处理静态资源的servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*",  "/favicon.ico");
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestMethod = request.getMethod().toLowerCase();
        String requestPath = request.getPathInfo();
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if(handler != null){
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            //创建请求参数对象
            Map<String, Object> paramMap = new HashMap<>();
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()){
                String paramName = parameterNames.nextElement();
                String paramValue = request.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }
            String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
            if(StringUtils.isNotEmpty(body)){
                String[] params = StringUtils.split(body, "&");
                if(ArrayUtils.isNotEmpty(params)){
                    for (String param : params){
                        String[] array = StringUtils.split(param, "=");
                        if(ArrayUtils.isNotEmpty(array) && array.length == 2){
                            String paramName = array[0];
                            String paramValue = array[1];
                            paramMap.put(paramName, paramValue);
                        }
                    }
                }
            }
            Param param = new Param(paramMap);
            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
            //处理Action方法返回值
            if(result instanceof View){
                //返回jsp
                View view = (View) result;
                String path = view.getPath();
                if(StringUtils.isNotBlank(path)){
                    if(path.startsWith("/")){
                        response.sendRedirect(request.getContextPath() + path);
                    }else{
                        Map<String, Object> model = view.getModel();
                        for (Map.Entry<String, Object> entry : model.entrySet()){
                            request.setAttribute(entry.getKey(), entry.getValue());
                        }
                        request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(request, response);
                    }
                }
            }else if(result instanceof Data){
                //返回json
                Data data = (Data) result;
                Object model = data.getModel();
                if(model != null){
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter writer = response.getWriter();
                    String json = JsonUtil.toJson(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }
        }
    }
}
