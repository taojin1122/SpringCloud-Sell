package com.reder.order.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 转换为json字符串
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 转换json为对象
     * @param string
     * @param
     * @return
     */
    public static Object fromJson(String string, Class classType){
        try {
            return objectMapper.readValue(string,classType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 转换json为对象
     * @param string
     * @param typeReference
     * @return
     */
    public static Object fromJson(String string, TypeReference typeReference){
        try {
            return objectMapper.readValue(string,typeReference);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
