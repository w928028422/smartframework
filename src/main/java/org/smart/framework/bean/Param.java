package org.smart.framework.bean;

import org.apache.commons.collections4.MapUtils;
import org.smart.framework.util.CastUtil;

import java.util.Map;

/**
 * 请求参数对象
 */
public class Param {

    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public long getLong(String name){
        return CastUtil.castLong(paramMap.get(name));
    }

    public boolean isEmpty(){
        return MapUtils.isEmpty(paramMap);
    }
}
