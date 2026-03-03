package com.yunny.channel.config;


import com.yunny.channel.common.constant.ServiceConstants;
import com.yunny.channel.common.util.StringUtil;
import com.yunny.channel.config.filter.PassHttpFilter;
import com.yunny.channel.config.filter.SessionTokenFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

/**
 * Spring加载过滤器 配置类
 */
@Configuration
public class MyServerConfig {


    public FilterRegistrationBean passHttpFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new PassHttpFilter());
        return registrationBean;
    }

//    /**
//     * token过滤的接口  （暂时不用，用拦截器过滤token）
//     * @return
//     */
//    @Bean
//    public FilterRegistrationBean sessionTokenFilter(){
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(new SessionTokenFilter());
//        registrationBean.setUrlPatterns( StringUtil.split( ServiceConstants.TOKEN_FILTER_URL,","));
//        return registrationBean;
//    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //指定文件大小
        factory.setMaxFileSize(DataSize.ofBytes(10485760*1024));//1G
        /// 设定上传文件大小
        factory.setMaxRequestSize(DataSize.ofBytes(10485760*1024));//1G
        return factory.createMultipartConfig();
    }


}
