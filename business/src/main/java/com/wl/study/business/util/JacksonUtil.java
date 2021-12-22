package com.wl.study.business.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @Description: jackson util
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/7/6
 */
public class JacksonUtil {
    private final static Logger log = LoggerFactory.getLogger(JacksonUtil.class);
    private volatile static ObjectMapper objectMapper = null;

    static {
        getInstance();
    }

    public static ObjectMapper getInstance() {
        if (Objects.isNull(objectMapper)) {
            synchronized (ObjectMapper.class) {
                if (Objects.isNull(objectMapper)) {
//                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);//排除json字符串中实体类没有的字段
                    objectMapper = new ObjectMapper();
                }
            }
        }
        return objectMapper;
    }

    /**
     * 解析json
     *
     * @param o
     * @return
     */
    public static String parseJson(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error("请求出错:", e);
        }
        return null;
    }

    /**
     * 读取json
     *
     * @param o
     * @param valueType
     * @param <T>
     * @return
     */
    public static <T> T readJson(String o, Class<T> valueType) {
        try {
            return objectMapper.readValue(o, valueType);
        } catch (JsonProcessingException e) {
            log.error("请求出错:", e);
        }
        return null;
    }

    /**
     * 读取json list
     *
     * @param o
     * @param cla
     * @param <T>
     * @return
     */
    public static <T> T readJsonList(String o, Class<T> cla) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, cla);
            return objectMapper.readValue(o, javaType);
        } catch (JsonProcessingException e) {
            log.error("请求出错:", e);
        }
        return null;
    }

    /**
     * 读取json set
     *
     * @param o
     * @param cla
     * @param <T>
     * @return
     */
    public static <T> T readJsonSet(String o, Class<T> cla) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(Set.class, cla);
            return objectMapper.readValue(o, javaType);
        } catch (JsonProcessingException e) {
            log.error("请求出错:", e);
        }
        return null;
    }
}
