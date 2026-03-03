package com.yunny.channel.service;

import com.yunny.channel.common.dto.SystemUserDTO;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.query.LoginUserQuery;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.SystemUserVO;
import com.yunny.channel.common.query.SystemUserQuery;

import java.util.List;

/**
 * Created by Fe
 */
public interface SystemUserService{

    CommonPager<SystemUserVO> listByPage(SystemUserQuery query);

    /**
    * 主键查询
    * @param id
    * @return
     */
    SystemUserVO getById(Long id);

    SystemUserVO getByUserNo(String userNo);


    /**
     * 插入
     * @return
     */
    BaseResult insertSelective(SystemUserDTO systemUserDto);

    /**
     * 修改
     * @return
     */
    int updateSelective(SystemUserDTO systemUserDto);


    List<SystemUserVO> queryUserBaseInfoDOList(LoginUserQuery query);

    /**
     * 修改密码
     * @param systemUserQuery
     * @return
     */
    BaseResult changePassword(SystemUserQuery systemUserQuery);


    List<SystemUserVO> getUsersByRoleId(Integer roleId);

}
