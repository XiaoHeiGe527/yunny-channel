package com.yunny.channel.feign.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Feign提供了一个接口RequestInterceptor，实现这个接口，就可以把接口的请求头部信息传递给feign接口
 */
@Configuration
public class FeignTokenConfiguration implements RequestInterceptor  {

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (attributes==null) {
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String values = request.getHeader(name);
                template.header(name, values);
                System.out.println("header put : {"+name+","+values+"}");
            }
        }
        Enumeration<String> bodyNames = request.getParameterNames();
        StringBuffer body =new StringBuffer();
        if (bodyNames != null) {
            while (bodyNames.hasMoreElements()) {
                String name = bodyNames.nextElement();
                String values = request.getParameter(name);
                body.append(name).append("=").append(values).append("&");
            }
        }
        if(body.length()!=0) {
            body.deleteCharAt(body.length()-1);
            template.body(body.toString());
            System.out.println("body put : {"+body.toString()+"}");
        }
    }
}

