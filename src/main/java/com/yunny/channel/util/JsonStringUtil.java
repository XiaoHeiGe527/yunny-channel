package com.yunny.channel.util;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

import java.util.*;
import java.util.stream.Collectors;

/**
 * sunfuwei
 */
public class JsonStringUtil {


    /**
     * 将JSON字符串 转换为 Map<String, String>
     * @param jsonString
     * @return
     */
    public static Map<String, String> toMapString(String jsonString){
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        Map<String, String> map = JSONObject.parseObject(jsonObject.toJSONString(), new TypeReference<Map<String, String>>(){});
        return map;
    }

    /**
     * 对象某个属性字符串集合转SJON
     * @param urlList
     * @return
     */
    public static String stringListoJSON(List<String> urlList){
        // 创建Gson实例
        Gson gson = new GsonBuilder().create();
        // 将List<String>转换为JSON字符串
        String jsonString = gson.toJson(urlList);
        return jsonString;

    }

    /**
     * JSON数组指定KEY的值转List<String>
     * @param jsonArr
     * @return
     */
    public static List<String> jsonArrValueToStringList(String jsonArr,String jsonKey){
        Gson gson = new Gson();
        Type type = new TypeToken<List<Map<String, String>>>(){}.getType();

        List<Map<String, String>> mapList = gson.fromJson(jsonArr, type);

        return mapList.stream()
            .map(map -> map.get(jsonKey))
            .collect(Collectors.toList());
    }

    /**
     * JSON数组指定KEY的值 Set<String>
     * @param json
     * @param jsonKey
     * @return
     * @throws Exception
     */
    public static Set<String> convertJsonToUrlSet(String json,String jsonKey) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Set<String> urlSet = new HashSet<>();
        mapper.readTree(json).forEach(node -> {
            urlSet.add(node.get(jsonKey).asText());
        });

        return urlSet;

    }


    public static Set<String> jsonToSetString(String json){
        Gson gson = new Gson();
        String[] stringArray = gson.fromJson(json, String[].class);
        // 将数组转换为 Set
        Set<String> stringSet = new HashSet<>(Arrays.asList(stringArray));
            return  stringSet;
    }




}
