package com.yunny.channel.common.config.oss;

import cn.hutool.core.util.ObjectUtil;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;

import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.DataRedundancyType;
import com.aliyun.oss.model.StorageClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AliyunOssUtils {

    private static AliyunOssProperties aliyunOssProperties;

    @Autowired
    public void setAliyunOssProperties(AliyunOssProperties aliyunOssProperties) {
        AliyunOssUtils.aliyunOssProperties = aliyunOssProperties;
    }

    /**
     * 创建存储空间
     */
    private static void createBucket(OSS ossClient) {
        // 判断存储空间是否存在
        if (ossClient.doesBucketExist(aliyunOssProperties.getBucketName())) {
            return;
        }

        // 创建CreateBucketRequest对象
        CreateBucketRequest request = new CreateBucketRequest(aliyunOssProperties.getBucketName());

        // 存储类型
        request.setStorageClass(StorageClass.Standard);
        // 数据容灾类型
        request.setDataRedundancyType(DataRedundancyType.ZRS);
        // 存储空间的读写权限
        request.setCannedACL(CannedAccessControlList.PublicRead);

        // 创建存储空间
        ossClient.createBucket(request);
    }

    /**
     * 获取OSSClient
     */
    public static OSS getOssClient() {
        // 创建ClientConfiguration。ClientConfiguration是OSSClient的配置类，可配置代理、连接超时、最大连接数等参数。
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        // 设置OSSClient允许打开的最大HTTP连接数，默认为1024个。
        conf.setMaxConnections(aliyunOssProperties.getMaxConnections());
        // 设置Socket层传输数据的超时时间，默认为50000毫秒。
        conf.setSocketTimeout(aliyunOssProperties.getSocketTimeout());
        // 设置建立连接的超时时间，默认为50000毫秒。
        conf.setConnectionTimeout(aliyunOssProperties.getConnectionTimeout());

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(aliyunOssProperties.getEndpoint(), aliyunOssProperties.getAccessKeyId(), aliyunOssProperties.getAccessKeySecret(), conf);

        // 创建存储空间
        createBucket(ossClient);

        return ossClient;
    }

    public static void shutdown(OSS ossClient) {
        if (ObjectUtil.isNotNull(ossClient)) {
            ossClient.shutdown();
        }
    }

}
