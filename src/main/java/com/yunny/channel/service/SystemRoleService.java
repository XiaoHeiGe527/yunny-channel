package com.yunny.channel.service;

import com.yunny.channel.common.dto.SystemRoleDTO;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.SystemRoleVO;
import com.yunny.channel.common.query.SystemRoleQuery;

import java.util.List;

/**
 * Created by Fe
 */
public interface SystemRoleService{

    CommonPager<SystemRoleVO> listByPage(SystemRoleQuery query);

     List<SystemRoleVO> listByQuery(SystemRoleQuery query);

    /**
    * 主键查询
    * @param id
    * @return
     */
    SystemRoleVO getById(Integer id);

    /**
     * 插入
     * @return
     */
    int insertSelective(SystemRoleDTO systemRoleDto);

    /**
     * 修改
     * @return
     */
    int updateSelective(SystemRoleDTO systemRoleDto);


    /**
     * 删除角色
     * @param id
     * @return
     */
    BaseResult deleteById(Integer id);

}
