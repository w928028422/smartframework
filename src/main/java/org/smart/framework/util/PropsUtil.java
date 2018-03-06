package org.smart.framework.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 属性工具类
 */
public final class PropsUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    public static Properties loadProps(String configFile){
        InputStream inputStream = null;
        Properties properties = new Properties();
        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFile);
            properties.load(inputStream);
        } catch (IOException e) {
            LOGGER.error("读取配置文件发生异常", e);
        }finally {
            if(inputStream != null){
                try{
                    inputStream.close();
                }catch (IOException e){
                    LOGGER.error("关闭输入流时发生异常", e);
                }
            }
        }
        return properties;
    }

    public static String getString(Properties configProps, String attr) {
        return configProps.getProperty(attr);
    }

    public static String getString(Properties configProps, String key, String def) {
        String value = def;
        if(configProps.contains(key)){
            value = configProps.getProperty(key);
        }
        return value;
    }
}
