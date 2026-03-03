package com.yunny.channel.config;


import com.yunny.channel.config.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 加载配置拦截器
 * WebMvcConfigurer配置类其实是Spring内部的一种配置方式，采用JavaBean的形式来代替传统的xml配置文件形式进行针对框架个性化定制，
 * 可以自定义一些Handler，Interceptor，ViewResolver，MessageConverter。基于java-based方式的spring mvc配置，
 * 需要创建一个配置类并实现WebMvcConfigurer 接口；
 */

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    private UserInterceptor userInterceptor;


    /* 拦截器配置 */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //后期可以配置成redis JSON 或者是nacos配置中心的JSON数组[ "/login/yunny/login", "/userInfo/testGet" ]
        List<String> patterns = new ArrayList<>();
        patterns.add("/sys/login");
        patterns.add("/sys/logout");
        patterns.add("/login/yunny/login");
        patterns.add("/login");
        patterns.add("/**/*.css");
        patterns.add("/**/*.js");
        patterns.add("/**/*.png");
        patterns.add("/**/*.jpg");
        patterns.add("/**/*.ico");
        patterns.add("/**/*.html");
        patterns.add("/**/*.map");
        patterns.add("/**/*.woff");
        patterns.add("/**/*.woff2");
        patterns.add("/static/**");
        registry.addInterceptor(userInterceptor)
                .excludePathPatterns(patterns);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                         .allowedOrigins("*")
                         .allowCredentials(true)
                         .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                         .maxAge(3600);
    }

    //常用的方法
//    /* 拦截器配置 */
//    void addInterceptors(InterceptorRegistry var1);
//    /* 视图跳转控制器 */
//    void addViewControllers(ViewControllerRegistry registry);
//    /**
//     *静态资源处理
//     **/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
            .addResourceLocations("classpath:/static/");
        System.out.println("静态资源映射已生效");
    }
//    /* 默认静态资源处理器 */
//    void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer);
//    /**
//     * 这里配置视图解析器
//     **/
//    void configureViewResolvers(ViewResolverRegistry registry);
//    /* 配置内容裁决的一些选项*/
//    void configureContentNegotiation(ContentNegotiationConfigurer configurer);
//    /** 解决跨域问题 **/
//    public void addCorsMappings(CorsRegistry registry) ;

}
