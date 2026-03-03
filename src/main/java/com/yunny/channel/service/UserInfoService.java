package com.yunny.channel.service;

import com.yunny.channel.common.dto.UserInfoDTO;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.UserBaseInfoVo;
import com.yunny.channel.common.vo.UserInfoVO;
import com.yunny.channel.common.query.UserInfoQuery;
import com.yunny.channel.common.page.CommonPager;

import java.util.List;

/**
 * Created by Fe
 */
public interface UserInfoService{

    CommonPager<UserInfoVO> listByPage(UserInfoQuery query);

    /**
    * 主键查询
    * @param id
    * @return
     */
    UserInfoVO getById(Long id);

    /**
     * 插入
     * @return
     */
    int insertSelective(UserInfoDTO userInfoDto);

    /**
     * 批量插入
     * @param userBaseInfoList
     * @return
     */
    int batchInsertSelective(List<UserBaseInfoVo> userBaseInfoList);

    /**
     * 修改
     * @return
     */
    int updateSelective(UserInfoDTO userInfoDto);


    /**
     * 定时将userBaseInfo数据库数据插入到用户信息表userInfo
     * @param jobparam 从第几页数据开始，第一页起始 填入0
     * @return
     */
    BaseResult insertUserInfoData(Long jobparam);


    /**
     * 测试feigen
     * @return
     */
    BaseResult insertUserInfoDataTestFeign(String token);

}
