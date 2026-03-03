package com.yunny.channel.sms.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//@RefreshScope //import org.springframework.cloud.context.config.annotation.RefreshScope;
@Component
@Data
public class AliyunSmsConfig {

    @Value("${aliyun.sms.accessKeyId}")
    private String accessKeyId;//你的accessKeyId,

    @Value("${aliyun.sms.accessKeySecret}")
    private String accessKeySecret;//你的accessKeySecret


    /**
     * 必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
     */
    @Value("${aliyun.sms.templateCode}")
    private String templateCode;



    private String regionId = "cn-hangzhou";
    /**
     * 短信签名场景（在阿里控制台配置）
     */
    private String signName = "翱游科技";
    /**
     * 短信API产品名称（短信产品名固定，无需修改）
     */
    private  final String product = "Dysmsapi";
    /**
     * 短信API产品域名（接口地址固定，无需修改）
     */
    private  final String domain = "dysmsapi.aliyuncs.com";

    /**
     * 短信API产品域名（接口地址固定，无需修改）
     */
    private  final String version = "2017-05-25";


}
