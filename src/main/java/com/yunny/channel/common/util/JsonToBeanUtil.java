package com.yunny.channel.common.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonToBeanUtil {

    public static  <T> T toBean(String json, Class<T> cls) {
        if (StrUtil.isNotBlank(json)) {
            return JSONUtil.toBean(json, cls);
        }
        return null;
    }

    public static  <T> T toBean(String json, Class<T> cls, Map<String, Class> classMap) {
        if(classMap==null){
            classMap= new HashMap<>();
        }
        return (T) JSONObject.toBean(JSONObject.fromObject(json), cls, classMap);
    }

    public static <T> List<T> toList(String json, Class<T> cls) {
        if (StrUtil.isNotBlank(json)) {
            return JSONUtil.toList(json, cls);
        }
        return null;
    }



}
