package com.yutown.notify.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.apache.commons.beanutils.BeanMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author dong jing xi
 * @date 2020/10/22 1:13
 **/
public class Utils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new ParameterNamesModule());
        OBJECT_MAPPER.registerModule(new Jdk8Module());
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        // 对象字段全部列入
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 取消默认转换timestamps形式
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        // 忽略空bean转json的错误
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        // 忽略在json字符串中存在,但是在java对象中不存在对应属性的情况
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    public static Map<String, Object> objectToMap(Object object) {
        return OBJECT_MAPPER.convertValue(object, Map.class);
    }


    public static List<String> emptyStringListIfNull(List<String> list) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        return list;
    }


}
