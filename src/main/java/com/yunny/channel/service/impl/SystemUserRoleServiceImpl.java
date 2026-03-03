package com.yunny.channel.service.impl;

import com.yunny.channel.common.entity.SystemUserRoleDO;
import com.yunny.channel.common.dto.SystemUserRoleDTO;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.vo.SystemUserRoleVO;
import com.yunny.channel.common.query.SystemUserRoleQuery;
import com.yunny.channel.mapper.SystemUserRoleMapper;
import com.yunny.channel.service.SystemUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import java.util.stream.Collectors;


/**
 * Created by Fe
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class SystemUserRoleServiceImpl implements SystemUserRoleService {

    @Resource
    private SystemUserRoleMapper systemUserRoleMapper;

    /**
    * 分页查询
    * @return
    */
    @Override
    public CommonPager<SystemUserRoleVO> listByPage(SystemUserRoleQuery systemUserRoleQuery) {
        PageParameter pageParameter = systemUserRoleQuery.getPageParameter();
        return new CommonPager<SystemUserRoleVO>(
                new PageParameter(pageParameter.getCurrentPage(), pageParameter.getPageSize(),
                        systemUserRoleMapper.countByQuery(systemUserRoleQuery)),
                systemUserRoleMapper.listByQuery(systemUserRoleQuery).stream()
                        .map(item -> {
                            SystemUserRoleVO systemUserRoleVo = new SystemUserRoleVO();
                            BeanUtils.copyProperties(item, systemUserRoleVo);
                            return systemUserRoleVo;
                        }).collect(Collectors.toList()));
    }

    /**
    * 通过id获取
    * @return
    */
    @Override
    public SystemUserRoleVO getById(Integer id) {
    	SystemUserRoleDO systemUserRoleDo = systemUserRoleMapper.getById(id);
    	if(null == systemUserRoleDo){
    		return null;
    	}
    	SystemUserRoleVO systemUserRoleVo = new SystemUserRoleVO();
    	BeanUtils.copyProperties(systemUserRoleDo, systemUserRoleVo);
    	return systemUserRoleVo;
    }


    @Override
    public SystemUserRoleVO getByUserNo(String userNo) {
        SystemUserRoleDO systemUserRoleDo = systemUserRoleMapper.getByUserNo(userNo);
        if(null == systemUserRoleDo){
            return null;
        }
        SystemUserRoleVO systemUserRoleVo = new SystemUserRoleVO();
        BeanUtils.copyProperties(systemUserRoleDo, systemUserRoleVo);
        return systemUserRoleVo;
    }


    /**
    * 新增
    * @return
    */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int insertSelective(SystemUserRoleDTO systemUserRoleDto) {
        SystemUserRoleDO systemUserRoleDo = new SystemUserRoleDO();
        BeanUtils.copyProperties(systemUserRoleDto, systemUserRoleDo);
        return systemUserRoleMapper.insertSelective(systemUserRoleDo);
    }

    /**
    * 修改
    * @return
    */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int updateSelective(SystemUserRoleDTO systemUserRoleDto) {
        SystemUserRoleDO systemUserRoleDo = new SystemUserRoleDO();
        BeanUtils.copyProperties(systemUserRoleDto, systemUserRoleDo);
        return systemUserRoleMapper.updateSelective(systemUserRoleDo);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int deleteInsert(SystemUserRoleDTO systemUserRoleDto) {

        systemUserRoleMapper.deleteByUserNo(systemUserRoleDto.getUserNo());

        return this.insertSelective(systemUserRoleDto);

    }



}
