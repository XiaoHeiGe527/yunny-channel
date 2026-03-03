package com.yunny.channel.common.util;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 作者：盛富强
 * 时间：2020/6/18 11:36
 * 描述：Http操作工具类
 **/
public class MyHttpUtils {
    /**
     * 从Request中取Headers
     *
     * @param request
     * @return
     */
    public static Map<String, Object> getHeaders(HttpServletRequest request) {
        Map<String, Object> headers = new LinkedHashMap<>();
        Enumeration<String> names = request.getHeaderNames();
        if (null != names && names.hasMoreElements()) {
            while (names.hasMoreElements()) {
                String name = names.nextElement();
                String value = request.getHeader(name);
                headers.put(name, value);
            }
        }
        return headers;
    }

    /**
     * 从Response中取Headers
     *
     * @param response
     * @return
     */
    public static Map<String, Object> getHeaders(HttpServletResponse response) {
        Map<String, Object> headers = new LinkedHashMap<>();
        Collection<String> names = response.getHeaderNames();
        if (CollectionUtil.isNotEmpty(names)) {
            for (String name : names) {
                String value = response.getHeader(name);
                headers.put(name, value);
            }
        }
        return headers;
    }

    /**
     * 从Request中提取请求参数
     *
     * @param request
     * @return
     */
    public static String getParams(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (CollectionUtil.isNotEmpty(parameterMap)) {
            return JSON.toJSONString(parameterMap);
        }
        String queryString = request.getQueryString();
        if (StringUtils.isNotBlank(queryString)) {
            return queryString;
        }
        Map<String, Object> paramsMap = new LinkedHashMap<>();
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String value = request.getParameter(name);
            paramsMap.put(name, value);
        }
        if (CollectionUtil.isNotEmpty(paramsMap)) {
            return JSON.toJSONString(paramsMap);
        }
        return "";
    }
}
