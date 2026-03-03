package com.yunny.channel.config.filter;


import com.yunny.channel.common.constant.ExceptionConstants;
import com.yunny.channel.common.constant.RedisKeyNameConstants;
import com.yunny.channel.common.tools.redis.RedisService;
import com.yunny.channel.common.util.HeaderMapRequestWrapper;
import com.yunny.channel.common.util.IDUtil;
import com.yunny.channel.common.util.ResUtil;
import com.yunny.channel.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 用户session过滤器 用过滤器校验token
 * @author sunfuwei521@qq.com
 */
@Slf4j
public class SessionTokenFilter implements Filter {

    @Autowired
    RedisService redisService;


    /**
     * 封装，不需要过滤的list列表（前方“/”拿掉）
     */
    protected static List<String> noFilterUrls = Arrays.asList("systemUser/login");


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //如果想要在Filter中执行Services方法，要在初始化Filter中执行以下方法（因为Filter执行顺序提前与Services）
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,filterConfig.getServletContext());
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        /**
         * 跨域问题
         */
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;

        httpResponse.setHeader("Access-Control-Allow-Origin", req.getHeader("origin"));//源网址
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE, HEAD");
        httpResponse.setHeader("Access-Control-Allow-Headers", "x-auth-token, x-requested-with, Content-Type,Origin," +
                " Content-Type, Cookie, Accept, multipart/form-data, application/json");
        httpResponse.addHeader("Access-Control-Allow-Headers","");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");
        //配置Content-disposition  不配置下面 跨域取不到 Content-disposition
        httpResponse.setHeader("Access-Control-Expose-Headers", "Content-Type,Content-Disposition");


        //OPTIONS 请求直接结束
        if (HttpMethod.OPTIONS.toString().equals(req.getMethod())) {

            chain.doFilter(request,response);
            return;
        }


        /**
         * 编码问题
         */
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");


        HeaderMapRequestWrapper requestWrapper = new HeaderMapRequestWrapper(req);

        /**
         * 获得URL,判断接口是否过滤
         */
        String url = req.getRequestURI().substring(req.getContextPath().length());
        if (url.startsWith("/") && url.length() > 1) {
            url = url.substring(1);
        }

        if (isInclude(url)){
            chain.doFilter(request,response);
            return;
        }

        /**
         * 获得token 校验token
         */
        String token = requestWrapper.getHeader("token");
        if(StringUtil.isEmpty(token) || StringUtil.isEmpty(token))
        {
            String result = ResUtil.convert2(String.valueOf(ExceptionConstants.PARAMETER_NULL), "false","token信息错误", null);
            ServletOutputStream out = response.getOutputStream();
            out.write(result.getBytes());
            out.flush();

        }else {
            try{

                /**
                 * 解密token  在获取Redis里的信息 进行用户token校验
                 */
                long userId = IDUtil.decodeUserIDFromToken(token);

                String nowToken =  redisService.getStringKey(RedisKeyNameConstants.SYSTEM_USER_TOKEN_KEY_PREFIX_REDIS + userId);

                String userNo =  redisService.getStringKey(RedisKeyNameConstants.SYSTEM_USER_NO_KEY_PREFIX_REDIS + userId);

                String networkId =  redisService.getStringKey(RedisKeyNameConstants.SYSTEM_USER_NETWORKID_KEY_PREFIX_REDIS + userId);


                if(StringUtil.isEmpty(userNo)||StringUtil.isEmpty(networkId))//session不存在
                {


                    String result = ResUtil.convert2(String.valueOf(ExceptionConstants.EXCEPTION_SESSION), "false","登陆信息错误，请重新登陆！", null);
                    ServletOutputStream out = response.getOutputStream();
                    out.write(result.getBytes());
                    out.flush();
                    return;

                }if(!token.equals(nowToken)){
                    String result = ResUtil.convert2(String.valueOf(ExceptionConstants.EXCEPTION_SESSION), "false","会话失效，请重新登陆！", null);
                    ServletOutputStream out = response.getOutputStream();
                    out.write(result.getBytes());
                    out.flush();
                    return;
                }
                else {

                //   //利用原始的request对象创建自己扩展的request对象并添加自定义参数
                //   RequestParameterWrapper requestParameterWrapper = new RequestParameterWrapper(req);
                //   requestParameterWrapper.addParameters(extraParams);

                    /**
                     * 将用户号与网点放入header 中
                     */
                    requestWrapper.addHeader("userNo", userNo);
                    requestWrapper.addHeader("networkId", networkId);

                    chain.doFilter(requestWrapper, response); // Goes to default servlet.
                }

            }catch (Exception e)
            {
                String result = ResUtil.convert2(String.valueOf(ExceptionConstants.EXCEPTION), "false","服务器异常:"+e.getMessage(),null);
                ServletOutputStream out = response.getOutputStream();
                out.write(result.getBytes());
                out.flush();

            }
        }

    }



    @Override
    public void destroy() {

    }

    /**
     * 是否需要过滤
     * @param url
     * @return
     */
    private boolean isInclude(String url) {
        for (String u : noFilterUrls) {
            if (u.equals(url)) {
                return true;
            }
        }
        return false;
    }


}
