package org.smart.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 编码解码工具类
 */
public final class CodecUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodecUtil.class);

    public static String decodeURL(String source) {
        String target;
        try{
            target = URLDecoder.decode(source, "UTF-8");
        }catch (Exception e){
            LOGGER.error("解码时发生异常", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    public static String encodeURL(String source){
        String target;
        try{
            target = URLEncoder.encode(source, "UTF-8");
        }catch (Exception e){
            LOGGER.error("编码时发生异常", e);
            throw new RuntimeException(e);
        }
        return target;
    }
}
