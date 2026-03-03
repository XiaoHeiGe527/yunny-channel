package com.yunny.channel.common.util;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * RespOutUtil响应输出Util
 *
 * @author lj
 */
@Slf4j
public class RespOutUtil {

    /**
     * 以JSON格式输出
     *
     * @param response
     */
    public static void outJson(HttpServletResponse response, Object resultVo) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
            // 将实体对象转换为JSON Object转换
            out.append(JSONUtil.toJsonStr(resultVo));
        } catch (IOException e) {
            log.error(">>>>>> JSON输出异常 ", e);
        }
    }

}
