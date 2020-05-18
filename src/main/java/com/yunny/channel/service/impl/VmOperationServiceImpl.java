package com.yunny.channel.service.impl;

import com.yunny.channel.common.constant.ExceptionConstants;
import com.yunny.channel.common.dto.VmIsExistAPI;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.feign.parameter.VmClientReq;
import com.yunny.channel.service.BaseApiService;
import com.yunny.channel.service.RequestBasicsAPIService;
import com.yunny.channel.service.VmOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VmOperationServiceImpl implements VmOperationService {


    @Autowired
    BaseApiService baseApiService;

    @Autowired
    RequestBasicsAPIService requestBasicsAPIService;


    @Override
    public BaseResult<Integer> queryvmIsExist(int type) {

        VmClientReq body = new VmClientReq();
        body.vm_config = 100;
        body.vm_type = type;
        BaseResult<VmIsExistAPI>  baseResult = requestBasicsAPIService.queryvmIsExistByApi(body);

        if(baseResult.getCode()!= ExceptionConstants.RESULT_CODE_SERVER_ERROR){
            log.error(baseResult.getMessage());
            BaseResult.failure(baseResult.getCode(),baseResult.getMessage());
        }

        VmIsExistAPI vmIsExistAPI = baseResult.getData();
        return BaseResult.success(vmIsExistAPI.getBalance());
    }
}
