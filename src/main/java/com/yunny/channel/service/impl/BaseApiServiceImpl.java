package com.yunny.channel.service.impl;

import com.yunny.channel.mapper.BaseApiMapper;
import com.yunny.channel.service.BaseApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseApiServiceImpl implements BaseApiService {

    @Autowired
    BaseApiMapper baseApiMapper;

    @Override
    public String getBaseApiUrlByVmType(int vmType) {
        return baseApiMapper.getBaseApiUrlByVmType(vmType);
    }
}
