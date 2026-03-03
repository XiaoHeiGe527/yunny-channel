package com.yunny.channel.config;

import com.yunny.channel.config.interceptor.CorsInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 加载拦截器类，暂时不用
 * @Configuration加载执行 跨域配置的拦截器 （暂时不用这个类已经在WebMvcConfiguration设置了跨域请求 ）
 */
//@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private CorsInterceptor corsInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 跨域拦截器需放在最上面
        registry.addInterceptor(corsInterceptor).addPathPatterns("/**");

    }
}
