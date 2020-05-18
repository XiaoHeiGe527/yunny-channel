package com.yunny.channel.feign.config;

import com.yunny.channel.feign.client.VmClient;
import feign.Feign;
import feign.Request;
import feign.Retryer;
import feign.Target;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyFeignConfig {


    /**
     * 设置 VmClient 超时时间
     * @return
     * @throws InterruptedException
     */
    @Bean
    public VmClient vmClient() throws InterruptedException {
        return Feign.builder().
                encoder(new JacksonEncoder()).
                decoder(new JacksonDecoder()).
                options(new Request.Options(60000, 60000))//ConnectTimeout 请求连接的超时时间 ReadTimeout请求处理的超时时间
                //.retryer(new Retryer.Default(5000, 5000, 3))//重试间隔 最大重试时间 重试次数
                .retryer(Retryer.NEVER_RETRY) //不重试
                .target(Target.EmptyTarget.create(VmClient.class));

    }
}
