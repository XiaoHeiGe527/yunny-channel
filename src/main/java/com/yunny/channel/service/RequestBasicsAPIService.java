package com.yunny.channel.service;

import com.yunny.channel.common.dto.VmIsExistAPI;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.feign.parameter.VmClientReq;

public interface RequestBasicsAPIService {

    /**
     * 查询虚机是否有库存
     * @param req
     * @return
     */
    BaseResult<VmIsExistAPI> queryvmIsExistByApi(VmClientReq req);
}
