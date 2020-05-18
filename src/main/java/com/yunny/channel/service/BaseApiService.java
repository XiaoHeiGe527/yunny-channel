package com.yunny.channel.service;

public interface BaseApiService {

    /**
     * 通过虚机类型查询API的URL路径
     * @param vmType
     * @return
     */
    String getBaseApiUrlByVmType(int vmType);
}
