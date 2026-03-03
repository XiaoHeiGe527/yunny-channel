package com.yunny.channel.oss.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
* @Description 
* @Author hex
* @CreateDate 2019/12/30 14:14
*/
@Configuration
public class AliyunOssConfig {

	/** endpoint域名 */
	@Value("${aliyun.oss.endpoint}")
	private String endpoint;

	/** id */
	@Value("${aliyun.oss.accessKeyId}")
	private String accessKeyId;

	/** 密码 */
	@Value("${aliyun.oss.accessKeySecret}")
	private String accessKeySecret;

	/** 名称 */
	@Value("${aliyun.oss.bucketName}")
	private String bucketName;

	/** bucket域名 */
	@Value("${aliyun.oss.urlPrefix}")
	private String urlPrefix;

	@Bean
	public OSS oSSClient() {
		return new OSSClient(endpoint, accessKeyId, accessKeySecret);
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getUrlPrefix() {
		return urlPrefix;
	}

	public void setUrlPrefix(String urlPrefix) {
		this.urlPrefix = urlPrefix;
	}
}
