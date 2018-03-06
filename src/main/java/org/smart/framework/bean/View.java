package org.smart.framework.bean;

import java.util.Map;

/**
 * 视图对象
 */
public class View {

    private String path;

    private Map<String, Object> model;

    public View(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public View addModel(String key, Object value){
        model.put(key, value);
        return this;
    }
}
