package com.yunny.channel.service;

import com.yunny.channel.common.result.BaseResult;

public interface VmOperationService {

    /**
     * 查询该类型的虚机是否有库存
     * @param type
     * @return
     */
    BaseResult<Integer> queryvmIsExist(int type);
}
