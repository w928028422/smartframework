package org.smart.framework.bean;

import java.util.Map;

/**
 * 数据对象
 */
public class Data {

    private Map<String, Object> model;

    public Data(Map<String, Object> model) {
        this.model = model;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
