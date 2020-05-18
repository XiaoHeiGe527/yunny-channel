package com.yunny.channel.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BaseApiMapper {

    /**
     * 通过虚机类型查询API的URL路径
     * @param vmType
     * @return
     */
    String getBaseApiUrlByVmType(@Param("vmType") int vmType);
}
