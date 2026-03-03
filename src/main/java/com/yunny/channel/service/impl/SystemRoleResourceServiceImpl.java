package com.yunny.channel.service.impl;

import com.yunny.channel.common.entity.SystemRoleResourceDO;
import com.yunny.channel.common.dto.SystemRoleResourceDTO;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.SystemRoleResourceVO;
import com.yunny.channel.common.query.SystemRoleResourceQuery;
import com.yunny.channel.mapper.SystemRoleResourceMapper;
import com.yunny.channel.service.SystemRoleResourceService;
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
public class SystemRoleResourceServiceImpl implements SystemRoleResourceService {

    @Resource
    private SystemRoleResourceMapper systemRoleResourceMapper;

    /**
    * 分页查询
    * @return
    */
    @Override
    public CommonPager<SystemRoleResourceVO> listByPage(SystemRoleResourceQuery systemRoleResourceQuery) {
        PageParameter pageParameter = systemRoleResourceQuery.getPageParameter();
        return new CommonPager<SystemRoleResourceVO>(
                new PageParameter(pageParameter.getCurrentPage(), pageParameter.getPageSize(),
                        systemRoleResourceMapper.countByQuery(systemRoleResourceQuery)),
                systemRoleResourceMapper.listByQuery(systemRoleResourceQuery).stream()
                        .map(item -> {
                            SystemRoleResourceVO systemRoleResourceVo = new SystemRoleResourceVO();
                            BeanUtils.copyProperties(item, systemRoleResourceVo);
                            return systemRoleResourceVo;
                        }).collect(Collectors.toList()));
    }

    /**
    * 通过id获取
    * @return
    */
    @Override
    public SystemRoleResourceVO getById(Long id) {
    	SystemRoleResourceDO systemRoleResourceDo = systemRoleResourceMapper.getById(id);
    	if(null == systemRoleResourceDo){
    		return null;
    	}
    	SystemRoleResourceVO systemRoleResourceVo = new SystemRoleResourceVO();
    	BeanUtils.copyProperties(systemRoleResourceDo, systemRoleResourceVo);
    	return systemRoleResourceVo;
    }

    /**
    * 新增
    * @return
    */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int insertSelective(SystemRoleResourceDTO systemRoleResourceDto) {
        SystemRoleResourceDO systemRoleResourceDo = new SystemRoleResourceDO();
        BeanUtils.copyProperties(systemRoleResourceDto, systemRoleResourceDo);
        return systemRoleResourceMapper.insertSelective(systemRoleResourceDo);
    }

    /**
    * 修改
    * @return
    */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int updateSelective(SystemRoleResourceDTO systemRoleResourceDto) {
        SystemRoleResourceDO systemRoleResourceDo = new SystemRoleResourceDO();
        BeanUtils.copyProperties(systemRoleResourceDto, systemRoleResourceDo);
        return systemRoleResourceMapper.updateSelective(systemRoleResourceDo);
    }

    @Override
    public int deleteByRoleId(Integer roleId) {
        return systemRoleResourceMapper.deleteByRoleId(roleId);
    }


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResult assignPermissions(SystemRoleResourceQuery systemRoleResourceQuery) {

        //先删除
        systemRoleResourceMapper.deleteByRoleId(systemRoleResourceQuery.getRoleId());
        //在绑定
        systemRoleResourceMapper.assignPermissions(systemRoleResourceQuery);

        return BaseResult.success();
    }

}
