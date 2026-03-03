package com.yunny.channel.feign.client;

import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.UserInfoVO;
import com.yunny.channel.feign.config.FeignTokenConfiguration;
import com.yunny.channel.feign.config.MyTestFeignConfig;


import com.yunny.channel.feign.parameter.VmClientReq;
import feign.Headers;
import feign.RequestLine;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.net.URI;

//@FeignClient(name = "/",configuration = MyTestFeignConfig.class) 这个是cloud微服务用的
//配置类是MyTestFeignConfig 类
@Headers({"Content-Type: application/json","Accept: application/json"})
public interface MyTestFeignClient {




//    @PostMapping("/userInfo/insertUserInfoData")
    @RequestLine("POST /userInfo/insertUserInfoData")
    public BaseResult insertUserInfoData(URI uri, @RequestHeader("token") String token);
}
