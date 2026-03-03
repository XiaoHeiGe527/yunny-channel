package com.yunny.channel.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ValueFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResUtil {
    private static ValueFilter filter = new ValueFilter() {
        @Override
        public Object process(Object obj, String s, Object v) {
            if (v == null)
                return "";
            return v;
        }
    };
    public static String convert( String res_code, String success,Object obj) {
        JSONObject json = new JSONObject();
        try
        {
            json.put("code", res_code);
            json.put("success", success);
            if (obj instanceof Map) {
                json.put("info", obj == null ? new HashMap() : obj);
            } else if (obj instanceof List) {
                List list = (List) obj;
                json.put("info", list == null ? new ArrayList() : list);
            } else if (obj instanceof String) {
                json.put("info", obj);
            } else {
                json.put("info", obj);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(json,filter);
    }

    public static String convert( String res_code, boolean success,String msg,Map map) {
        JSONObject json = new JSONObject();
        try
        {
            json.put("code", res_code);
            json.put("success", success);
            json.put("msg", msg);

            map.forEach((key, value) -> {
                if (value instanceof Map) {
                    json.put(key.toString(), value == null ? new HashMap() : value);
                } else if (value instanceof List) {
                    List list = (List) value;
                    json.put(key.toString(), list == null ? new ArrayList() : list);
                } else if (value instanceof String) {
                    json.put(key.toString(), value);
                } else {
                    json.put(key.toString(), value);
                }

            });



        } catch(Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(json,filter);
    }

    public static String convert2( String res_code, String success,String msg,Object obj) {
        JSONObject json = new JSONObject();
        try
        {
            json.put("code", res_code);
            json.put("success", success);
            json.put("msg", msg);
            if (obj instanceof Map) {
                json.put("info", obj == null ? new HashMap() : obj);
            } else if (obj instanceof List) {
                List list = (List) obj;
                json.put("info", list == null ? new ArrayList() : list);
            } else if (obj instanceof String) {
                json.put("info", obj);
            } else {
                json.put("info", obj);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(json,filter);

    }
}