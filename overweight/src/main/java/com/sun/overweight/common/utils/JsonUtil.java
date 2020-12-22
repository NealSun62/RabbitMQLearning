package com.sun.overweight.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * JSON 工具类
 * 
 * @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)在类上加
 * @author neal.sun
 * @since 1.0.0
 */
public final class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtil() {
    }

    /**
     * 将 POJO 对象转为 JSON 字符串
     */
    public static <T> String toJson(T pojo) {
        String json = null;

        if (pojo != null) {
            try {
                json = objectMapper.writeValueAsString(pojo);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return json;
    }

    /**
     * 将 JSON 字符串转为 POJO 对象
     */
    public static <T> T fromJson(String json, Class<T> type) {
        T pojo = null;

        if (json != null && !json.isEmpty()) {
            try {
                pojo = objectMapper.readValue(json, type);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return pojo;
    }

    /**
     * json数组转List
     * 
     * List<UserBean> jsonToUserBeans = JacksonUtil.readValue(listToJson, new TypeReference<List<UserBean>>() {
     * 
     * @param json
     * @param valueTypeRef
     * @return
     */
    public static <T> T fromJsonToList(String json, TypeReference<T> valueTypeRef) {
        T list = null;

        try {
            list = objectMapper.readValue(json, valueTypeRef);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    /**
     * json转map
     * 
     * @param json
     * @return
     *
     * @author neal.sun
     */
    @SuppressWarnings("unchecked")
    public static <T> Map<String, Map<String, Object>> fromJsonToMap(String json) {
        Map<String, Map<String, Object>> map = null;

        try {
            map = objectMapper.readValue(json, Map.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return map;
    }

    /**
     * json字符串转TreeNode
     * 
     * @param json
     * @return
     *
     * @author neal.sun
     */
    public static <T> JsonNode fromJsonToTreeNode(String json) {
        JsonNode node = null;
        try {
            node = objectMapper.readTree(json);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return node;
    }

}
