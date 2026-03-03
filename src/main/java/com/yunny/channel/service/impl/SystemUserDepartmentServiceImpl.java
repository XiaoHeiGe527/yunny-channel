package com.yunny.channel.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.yunny.channel.common.entity.SystemUserDepartmentDO;
import com.yunny.channel.common.dto.SystemUserDepartmentDTO;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.vo.SystemUserDepartmentVO;
import com.yunny.channel.common.query.SystemUserDepartmentQuery;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.mapper.SystemUserDepartmentMapper;
import com.yunny.channel.service.SystemUserDepartmentService;
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
public class SystemUserDepartmentServiceImpl implements SystemUserDepartmentService {

    @Resource
    private SystemUserDepartmentMapper systemUserDepartmentMapper;

    /**
    * 分页查询
    * @return
    */
    @Override
    public CommonPager<SystemUserDepartmentVO> listByPage(SystemUserDepartmentQuery systemUserDepartmentQuery) {
        PageParameter pageParameter = systemUserDepartmentQuery.getPageParameter();
        return new CommonPager<SystemUserDepartmentVO>(
                new PageParameter(pageParameter.getCurrentPage(), pageParameter.getPageSize(),
                        systemUserDepartmentMapper.countByQuery(systemUserDepartmentQuery)),
                systemUserDepartmentMapper.listByQuery(systemUserDepartmentQuery).stream()
                        .map(item -> {
                            SystemUserDepartmentVO systemUserDepartmentVo = new SystemUserDepartmentVO();
                            BeanUtils.copyProperties(item, systemUserDepartmentVo);
                            return systemUserDepartmentVo;
                        }).collect(Collectors.toList()));
    }

    /**
    * 通过id获取
    * @return
    */
    @Override
    public SystemUserDepartmentVO getById(Long id) {
    	SystemUserDepartmentDO systemUserDepartmentDo = systemUserDepartmentMapper.getById(id);
    	if(null == systemUserDepartmentDo){
    		return null;
    	}
    	SystemUserDepartmentVO systemUserDepartmentVo = new SystemUserDepartmentVO();
    	BeanUtils.copyProperties(systemUserDepartmentDo, systemUserDepartmentVo);
    	return systemUserDepartmentVo;
    }

    /**
    * 新增
    * @return
    */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int insertSelective(SystemUserDepartmentDTO systemUserDepartmentDto) {
        SystemUserDepartmentDO systemUserDepartmentDo = new SystemUserDepartmentDO();
        BeanUtils.copyProperties(systemUserDepartmentDto, systemUserDepartmentDo);
        return systemUserDepartmentMapper.insertSelective(systemUserDepartmentDo);
    }

    /**
    * 修改
    * @return
    */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int updateSelective(SystemUserDepartmentDTO systemUserDepartmentDto) {
        SystemUserDepartmentDO systemUserDepartmentDo = new SystemUserDepartmentDO();
        BeanUtils.copyProperties(systemUserDepartmentDto, systemUserDepartmentDo);
        return systemUserDepartmentMapper.updateSelective(systemUserDepartmentDo);
    }

    @Override
    public SystemUserDepartmentVO getUserDepartment(SystemUserDepartmentQuery query) {
        List<SystemUserDepartmentDO> list = systemUserDepartmentMapper.listByUserDepartment(query);
        if(CollectionUtil.isNotEmpty(list)){
            SystemUserDepartmentVO materialPlanDetailVo = new SystemUserDepartmentVO();
            BeanUtils.copyProperties(list.get(0), materialPlanDetailVo);
           return materialPlanDetailVo;

        }

        return null;
    }


}
