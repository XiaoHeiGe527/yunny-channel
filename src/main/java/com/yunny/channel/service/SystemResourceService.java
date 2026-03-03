package com.yunny.channel.service;

import com.yunny.channel.common.dto.SystemResourceDTO;
import com.yunny.channel.common.dto.SystemRoleResourceDTO;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.SystemResourceVO;
import com.yunny.channel.common.query.SystemResourceQuery;

import java.util.List;

/**
 * Created by Fe
 */
public interface SystemResourceService{

    CommonPager<SystemResourceVO> listByPage(SystemResourceQuery query);

    List<SystemResourceVO> listByQuery(SystemResourceQuery query);

    List<SystemResourceVO> listByRoleId(SystemResourceQuery query);


    /**
    * 主键查询
    * @param id
    * @return
     */
    SystemResourceVO getById(Integer id);

    /**
     * 插入
     * @return
     */
    int insertSelective(SystemResourceDTO systemResourceDto);

    /**
     * 修改
     * @return
     */
    int updateSelective(SystemResourceDTO systemResourceDto);

    /**
     * 查询用户的所有权限路径的JSON
     * @param userNo
     * @return
     */
    String selectUserResourceListJSON(String userNo);

    /**
     * 查询用户的所有权限路径
     * @param userNo
     * @return
     */
    List<String> selectUserResourceList(String userNo);

    BaseResult resourceDQ();



    List<SystemResourceVO> selectUserValid2LevelResourcesByUserNo(String userNo);

}
