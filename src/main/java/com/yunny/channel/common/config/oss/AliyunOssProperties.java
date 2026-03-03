package com.yunny.channel.common.config.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOssProperties {

    /**
     * 是否开启
     */
    private boolean enable;

    /**
     * 访问域名
     */
    private String endpoint;

    /**
     * 存储空间
     */
    private String bucketName;

    /**
     * 文件夹
     */
    private String folderName;

    /**
     * 访问用户
     */
    private String accessKeyId;

    /**
     * 访问密钥
     */
    private String accessKeySecret;

    /**
     * 允许打开的最大HTTP连接数
     */
    private int maxConnections;

    /**
     * Socket层传输数据的超时时间
     */
    private int socketTimeout;

    /**
     * 建立连接的超时时间
     */
    private int connectionTimeout;

}
