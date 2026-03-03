package com.yunny.channel.config.filter;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 跨域过滤器
 */
@Component
@WebFilter(urlPatterns = "/*", filterName = "authFilter") //这里的“/*” 表示的是需要拦截的请求路径
public class PassHttpFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse)servletResponse;
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        httpResponse.setHeader("Access-Control-Allow-Origin", httpRequest.getHeader("origin"));//源网址
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE, HEAD");
        httpResponse.setHeader("Access-Control-Allow-Headers", "x-auth-token, x-requested-with, Content-Type,Origin," +
                " Content-Type, Cookie, Accept, multipart/form-data, application/json");
        httpResponse.addHeader("Access-Control-Allow-Headers","");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");
        httpResponse.setHeader("Access-Control-Expose-Headers", "Content-Type,Content-Disposition");

        //OPTIONS 请求直接结束 跨域会多出OPTIONS 请求
        if (HttpMethod.OPTIONS.toString().equals(httpRequest.getMethod())) {

            filterChain.doFilter(servletRequest, httpResponse);
            return;
        }


        filterChain.doFilter(servletRequest, httpResponse);
    }
    @Override
    public void destroy() { }
}
