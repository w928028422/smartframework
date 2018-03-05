package org.smart.framework.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * json工具类
 */
public final class JsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static <T> String toJson(T object) {
        String json;
        try{
            json = MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOGGER.error("pojo转换为json时发生异常", e);
            throw new RuntimeException(e);
        }
        return json;
    }

    public static <T> T fromJson(String json, Class<T> type) {
        T pojo;
        try{
            pojo = MAPPER.readValue(json, type);
        } catch (IOException e) {
            LOGGER.error("json转换为pojo时发生异常", e);
            throw new RuntimeException(e);
        }
        return pojo;
    }
}
