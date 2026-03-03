package com.yunny.channel.common.tools.aspect.logger;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import lombok.Builder;
import lombok.Data;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;
import org.aspectj.lang.JoinPoint;

@Data
@Builder
public class LoggerVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 处理时间
     */
    private long times;

    /**
     * IP地址
     */
    private String ipAddr;

    /**
     * 方法名
     */
    private String method;

    /**
     * 请求路径
     */
    private String href;

    /**
     * 请求参数
     */
    private List<Object> params;
    /**
     * 返回信息
     */
    private Object result;

    /**
     * 异常记录
     */
    private String errorMsg;

    /**
     * 获取参数列表
     *
     * @param jp
     */
    public static List<Object> getParams(JoinPoint jp) {
        List<Object> params = CollUtil.toList();
        if (ArrayUtil.isNotEmpty(jp.getArgs())) {
            for (Object o : jp.getArgs()) {
                if (!(o instanceof HttpServletRequest) && !(o instanceof HttpServletResponse)) {
                    params.add(o);
                }
            }
        }
        return params;
    }
}
