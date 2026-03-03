package com.yunny.channel.service;

import com.yunny.channel.common.dto.SystemUserRoleDTO;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.vo.SystemUserRoleVO;
import com.yunny.channel.common.query.SystemUserRoleQuery;

/**
 * Created by Fe
 */
public interface SystemUserRoleService{

    CommonPager<SystemUserRoleVO> listByPage(SystemUserRoleQuery query);

    /**
    * 主键查询
    * @param id
    * @return
     */
    SystemUserRoleVO getById(Integer id);


    SystemUserRoleVO getByUserNo(String userNo);

    /**
     * 插入
     * @return
     */
    int insertSelective(SystemUserRoleDTO systemUserRoleDto);

    /**
     * 修改
     * @return
     */
    int updateSelective(SystemUserRoleDTO systemUserRoleDto);


    /**
     * 先删除在插入
     * @param systemUserRoleDto
     * @return
     */
    int  deleteInsert(SystemUserRoleDTO systemUserRoleDto);


}
