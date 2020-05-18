package com.yunny.channel.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yunny.channel.common.constant.ExceptionConstants;
import com.yunny.channel.common.dto.VmIsExistAPI;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.feign.client.VmClient;
import com.yunny.channel.feign.parameter.VmClientReq;
import com.yunny.channel.service.BaseApiService;
import com.yunny.channel.service.RequestBasicsAPIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;


@Slf4j
@Service
public class RequestBasicsAPIServiceImpl implements RequestBasicsAPIService {

    @Autowired
    BaseApiService baseApiService;

    @Autowired
    VmClient vmClient;

    @Override
    public BaseResult<VmIsExistAPI> queryvmIsExistByApi(VmClientReq req) {

        //根据虚机类型获得API地址
        String uri = baseApiService.getBaseApiUrlByVmType(req.vm_type);

        Map map = null;
        try {
            map = vmClient.vm_is_exist(new URI(uri),req);
        } catch (URISyntaxException e) {
            log.error("查询虚机库存失败:[{}]",e);
            log.info("查询虚机库存失败:[{}]",e);
            return BaseResult.failure(ExceptionConstants.RESULT_CODE_SERVER_ERROR,"失败信息:"+e.getMessage());

        }

        String msg = (String) map.get("msg");
        boolean success = (boolean) map.get("success");

        if (!success) {
            return BaseResult.failure(ExceptionConstants.RESULT_CODE_SERVER_ERROR,"查询库存信息失败:"+msg);

        }

        String  balance = JSONObject.toJSONString(map.get("data"));

        VmIsExistAPI vmIsExistAPI =JSONObject.parseObject(balance,VmIsExistAPI.class);
        return BaseResult.success(vmIsExistAPI);

    }
}
