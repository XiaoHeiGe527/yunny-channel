package com.yunny.channel.service;

import com.yunny.channel.common.dto.SystemRoleResourceDTO;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.SystemRoleResourceVO;
import com.yunny.channel.common.query.SystemRoleResourceQuery;

import java.util.List;

/**
 * Created by Fe
 */
public interface SystemRoleResourceService{

    CommonPager<SystemRoleResourceVO> listByPage(SystemRoleResourceQuery query);

    /**
    * 主键查询
    * @param id
    * @return
     */
    SystemRoleResourceVO getById(Long id);

    /**
     * 插入
     * @return
     */
    int insertSelective(SystemRoleResourceDTO systemRoleResourceDto);

    /**
     * 修改
     * @return
     */
    int updateSelective(SystemRoleResourceDTO systemRoleResourceDto);


    int deleteByRoleId(Integer roleId);



    BaseResult assignPermissions(SystemRoleResourceQuery systemRoleResourceQuery);

}
