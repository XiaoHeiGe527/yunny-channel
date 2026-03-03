package com.yunny.channel.config.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.google.gson.Gson;
import com.yunny.channel.common.constant.ExceptionConstants;
import com.yunny.channel.common.constant.JumpUrlConstants;
import com.yunny.channel.common.constant.RedisKeyNameConstants;
import com.yunny.channel.common.dto.EmployeeDTO;
import com.yunny.channel.common.exception.PermissionException;
import com.yunny.channel.common.exception.ServiceException;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.tools.redis.RedisService;
import com.yunny.channel.common.util.IPUtils;
import com.yunny.channel.common.vo.SystemUserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static com.yunny.channel.common.constant.ExceptionConstants.RESULT_CODE_UNAUTHORIZED;

/**
 *
 * @author
 */
@Slf4j
@Component
public class UserInterceptor implements HandlerInterceptor {

    public static final String REQUEST_METHOD_OPTIONS = "OPTIONS";


    public static final String PERMISSION_EXCEPTION_REDIRECT_URL = JumpUrlConstants.CAR_INSURANCE_LOGIN;

    @Lazy
    @Autowired
    RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        // 由于拦截器会先于跨域配置（WebMvcConfiguration）响应，因此手动设置允许跨域响应头
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Headers","*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods","*");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials","true");
        httpServletResponse.setHeader("Access-Control-Max-Age","3600");

        // 当请求为OPTIONS方法的时，表示是浏览器跨域发出的域请求，直接返回
        String method = httpServletRequest.getMethod();
        if (Objects.equals(REQUEST_METHOD_OPTIONS, method.toUpperCase())) {
            return true;
        }

        /**
         * 获得URL,判断接口是否过滤
         */
        String url = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length());
        if (url.startsWith("/") && url.length() > 1) {
            url = url.substring(1);
        }
        String ip = IPUtils.getIpAddr(httpServletRequest);
        log.info("当前请求服务的IP信息是：[{}],url信息是:[{}]",ip,url);
        // 优先从请求参数中获取 token
        String token = httpServletRequest.getParameter("token");
        log.info("token:[{}]",token);
        if (StringUtils.isBlank(token)) {
            // 若参数中没有，再从请求头中获取
            Enumeration<String> tokenHeaders = httpServletRequest.getHeaders("token");
            if (tokenHeaders.hasMoreElements()) {
                while (tokenHeaders.hasMoreElements()) {
                    token = tokenHeaders.nextElement();
                }
            } else {
                token = httpServletRequest.getHeader("token");
            }
        }

        if (StringUtils.isBlank(token)) {
            log.error("token信息不正确：{}", JSON.toJSONString(token

            ));
            // token无效也跳转到登录页
            httpServletRequest.setAttribute("errorMsg", "登录已过期，请重新登录");
            throw new PermissionException("用户未登录",PERMISSION_EXCEPTION_REDIRECT_URL);
        }
                     //1128  412679387
//                LocalDateTime currentTime = LocalDateTime.now();LocalDateTime deadline =
//                LocalDateTime.of(2026, Month.MAY, 25, 23, 59, 59);
//                if (currentTime.isAfter(deadline)) {
//                    Random random = new Random();
//                    int randomNumber = random.nextInt(10) + 1;
////                if(randomNumber>8){
////                    throw new PermissionException("",PERMISSION_EXCEPTION_REDIRECT_URL);
////                }
//}
        String value = redisService.getStringKey(RedisKeyNameConstants.USER_TOKEN_KEY_PREFIX_REDIS + token);

        Gson gson = new Gson();

        SystemUserVO userVO = gson.fromJson(value, SystemUserVO.class);

        if (StringUtils.isBlank(value)) {
            log.error("token信息不正确：{}", JSON.toJSONString(value));
            throw new PermissionException("用户未登录",PERMISSION_EXCEPTION_REDIRECT_URL);
        }


        if (Objects.nonNull(userVO)) {
            httpServletRequest.setAttribute("userNo", userVO.getUserNo());
            //写入token
            httpServletRequest.setAttribute("token", token);

            return true;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}