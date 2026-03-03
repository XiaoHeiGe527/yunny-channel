package com.yunny.channel.service;

import com.yunny.channel.common.dto.SystemUserDepartmentDTO;
import com.yunny.channel.common.entity.SystemUserDepartmentDO;
import com.yunny.channel.common.vo.SystemUserDepartmentVO;
import com.yunny.channel.common.query.SystemUserDepartmentQuery;
import com.yunny.channel.common.page.CommonPager;

/**
 * Created by Fe
 */
public interface SystemUserDepartmentService{

    CommonPager<SystemUserDepartmentVO> listByPage(SystemUserDepartmentQuery query);

    /**
    * 主键查询
    * @param id
    * @return
     */
    SystemUserDepartmentVO getById(Long id);

    /**
     * 插入
     * @return
     */
    int insertSelective(SystemUserDepartmentDTO systemUserDepartmentDto);

    /**
     * 修改
     * @return
     */
    int updateSelective(SystemUserDepartmentDTO systemUserDepartmentDto);


    /**
     * 获取用户部门（单个）
     * @param query
     * @return
     */
    SystemUserDepartmentVO getUserDepartment(SystemUserDepartmentQuery query);


}
