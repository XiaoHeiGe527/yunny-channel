package com.yunny.channel.service;

import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.query.UserBaseInfoQuery;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.UserBaseInfoVo;

import java.util.List;


public interface UserBaseInfoService {

    /**
     * 普通查询
     * @param userBaseInfoQuery
     * @return
     */
    List<UserBaseInfoVo> selectUserBaseInfoList(UserBaseInfoQuery userBaseInfoQuery);

    /**
     * 分页查询
     *
     * @param userBaseInfoQuery
     * @return
     */
    CommonPager<UserBaseInfoVo> listByQuery(UserBaseInfoQuery userBaseInfoQuery);

    /**
     * 锁用户号
     * 获取redis 存入的值
     * 发送MQ消息
     * @param userNo
     * @return
     */
    BaseResult testSendMq(String userNo);

}
