package com.ritian.common.util;

import java.util.HashMap;

/**
 * @author ritian.Zhang
 * @date 2019/01/10
 **/
public class MapUtils extends HashMap<String, Object> {

    @Override
    public MapUtils put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
