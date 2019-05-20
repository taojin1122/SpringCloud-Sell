package com.reder.product.utils;


import com.alibaba.fastjson.JSON;

public class JsonUtil {
    public static String toJson(Object object) {
        return JSON.toJSON(object).toString();
    }
}
