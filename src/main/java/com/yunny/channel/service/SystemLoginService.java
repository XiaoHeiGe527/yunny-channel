package com.yunny.channel.service;

import com.yunny.channel.common.query.YunnyLoginQuery;
import com.yunny.channel.common.result.BaseResult;

public interface SystemLoginService {


    /**
     * 用户登录
     * @param command
     * @return
     */
    BaseResult yunnyLogin(YunnyLoginQuery command);

    /**
     * 退出登录
     * @param token
     */
    void logout(String token,String userNo);
}
