package com.yunny.channel.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yunny.channel.common.entity.SystemResourceDO;
import com.yunny.channel.common.dto.SystemResourceDTO;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.SystemResourceVO;
import com.yunny.channel.common.query.SystemResourceQuery;
import com.yunny.channel.mapper.SystemResourceMapper;
import com.yunny.channel.service.SystemResourceService;
import com.yunny.channel.service.SystemRoleResourceService;
import com.yunny.channel.util.JsonStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
@Slf4j @Service @Transactional(rollbackFor = Throwable.class) public class SystemResourceServiceImpl
    implements SystemResourceService {

    @Resource private SystemResourceMapper systemResourceMapper;

    @Autowired
    SystemRoleResourceService systemRoleResourceService;

    /**
     * 分页查询
     *
     * @return
     */
    @Override public CommonPager<SystemResourceVO> listByPage(SystemResourceQuery systemResourceQuery) {
        PageParameter pageParameter = systemResourceQuery.getPageParameter();
        return new CommonPager<SystemResourceVO>(
            new PageParameter(pageParameter.getCurrentPage(), pageParameter.getPageSize(),
                systemResourceMapper.countByQuery(systemResourceQuery)),
            systemResourceMapper.listByQuery(systemResourceQuery).stream().map(item -> {
                SystemResourceVO systemResourceVo = new SystemResourceVO();
                BeanUtils.copyProperties(item, systemResourceVo);
                return systemResourceVo;
            }).collect(Collectors.toList()));
    }

    @Override
    public List<SystemResourceVO> listByQuery(SystemResourceQuery query) {
       return systemResourceMapper.listByQuery(query).stream().map(item ->{
            SystemResourceVO systemResourceVo = new SystemResourceVO();
            BeanUtils.copyProperties(item, systemResourceVo);
            return systemResourceVo;
        })
        .collect(Collectors.toList());
    }


    @Override
    public List<SystemResourceVO> listByRoleId(SystemResourceQuery query) {
        return systemResourceMapper.listByRoleId(query.getRoleId()).stream().map(item ->{
            SystemResourceVO systemResourceVo = new SystemResourceVO();
            BeanUtils.copyProperties(item, systemResourceVo);
            return systemResourceVo;
        })
                .collect(Collectors.toList());
    }

    /**
     * 通过id获取
     *
     * @return
     */
    @Override public SystemResourceVO getById(Integer id) {
        SystemResourceDO systemResourceDo = systemResourceMapper.getById(id);
        if (null == systemResourceDo) {
            return null;
        }
        SystemResourceVO systemResourceVo = new SystemResourceVO();
        BeanUtils.copyProperties(systemResourceDo, systemResourceVo);
        return systemResourceVo;
    }

    /**
     * 新增
     *
     * @return
     */
    @Override @Transactional(rollbackFor = Throwable.class) public int insertSelective(
        SystemResourceDTO systemResourceDto) {
        SystemResourceDO systemResourceDo = new SystemResourceDO();
        BeanUtils.copyProperties(systemResourceDto, systemResourceDo);
        return systemResourceMapper.insertSelective(systemResourceDo);
    }

    /**
     * 修改
     *
     * @return
     */
    @Override @Transactional(rollbackFor = Throwable.class) public int updateSelective(
        SystemResourceDTO systemResourceDto) {
        SystemResourceDO systemResourceDo = new SystemResourceDO();
        BeanUtils.copyProperties(systemResourceDto, systemResourceDo);
        return systemResourceMapper.updateSelective(systemResourceDo);
    }

    @Override public String selectUserResourceListJSON(String userNo) {
        List<String> urlList = systemResourceMapper.selectUserResourceList(userNo);
        return   JsonStringUtil.stringListoJSON(urlList);
    }

    @Override public List<String> selectUserResourceList(String userNo) {
        return systemResourceMapper.selectUserResourceList(userNo);
    }

    @Override
    public BaseResult resourceDQ() {
        return BaseResult.success(systemResourceMapper.resourceDQ());
    }

    @Override
    public List<SystemResourceVO> selectUserValid2LevelResourcesByUserNo(String userNo) {
      return   systemResourceMapper.selectUserValid2LevelResourcesByUserNo(userNo).stream().map(item -> {
            SystemResourceVO systemResourceVo = new SystemResourceVO();
            BeanUtils.copyProperties(item, systemResourceVo);
            return systemResourceVo;
        }).collect(Collectors.toList());
    }

}
