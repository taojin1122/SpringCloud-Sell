package com.reder.server.utils;

import com.alibaba.fastjson.JSON;


public class JsonUtil {
    public static <T>T toObject(String str, Class<T> object) {

       return JSON.parseObject(str, object);
    }
}
