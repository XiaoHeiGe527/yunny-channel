package com.yunny.channel.service;

import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.query.LoginUserQuery;
import com.yunny.channel.common.query.UserBaseInfoQuery;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.UserBaseInfoVo;
import com.yunny.channel.common.util.excel.constant.UserBaseInfoExcel;

import java.util.List;


public interface UserBaseInfoService {

    /**
     * 普通查询
     * @param userBaseInfoQuery
     * @return
     */
    List<UserBaseInfoVo> selectUserBaseInfoList(UserBaseInfoQuery userBaseInfoQuery);

    /**
     * 登录用户查询
     * @param query
     * @return
     */
    List<UserBaseInfoVo>  queryUserBaseInfoDOList(LoginUserQuery query);

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

    /**
     * 多线程处理数据
     * @param count
     * @return
     */
    BaseResult testExecutorStr(int count);


    List<UserBaseInfoExcel> findList(UserBaseInfoQuery query);

}
