package com.yunny.channel.service.impl;

import com.yunny.channel.common.entity.SystemLogDO;
import com.yunny.channel.mapper.SystemLogMapper;
import com.yunny.channel.common.dto.SystemLogDTO;
import com.yunny.channel.common.vo.SystemLogVO;
import com.yunny.channel.common.query.SystemLogQuery;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;

import com.yunny.channel.service.SystemLogService;
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
public class SystemLogServiceImpl implements SystemLogService {

    @Resource
    private SystemLogMapper systemLogMapper;

    /**
    * 分页查询
    * @return
    */
    @Override
    public CommonPager<SystemLogVO> listByPage(SystemLogQuery systemLogQuery) {
        PageParameter pageParameter = systemLogQuery.getPageParameter();
        return new CommonPager<SystemLogVO>(
                new PageParameter(pageParameter.getCurrentPage(), pageParameter.getPageSize(),
                        systemLogMapper.countByQuery(systemLogQuery)),
                systemLogMapper.listByQuery(systemLogQuery).stream()
                        .map(item -> {
                            SystemLogVO systemLogVo = new SystemLogVO();
                            BeanUtils.copyProperties(item, systemLogVo);
                            return systemLogVo;
                        }).collect(Collectors.toList()));
    }

    /**
    * 通过id获取
    * @return
    */
    @Override
    public SystemLogVO getById(Long id) {
    	SystemLogDO systemLogDo = systemLogMapper.getById(id);
    	if(null == systemLogDo){
    		return null;
    	}
    	SystemLogVO systemLogVo = new SystemLogVO();
    	BeanUtils.copyProperties(systemLogDo, systemLogVo);
    	return systemLogVo;
    }

    /**
    * 新增
    * @return
    */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int insertSelective(SystemLogDTO systemLogDto) {
        SystemLogDO systemLogDo = new SystemLogDO();
        BeanUtils.copyProperties(systemLogDto, systemLogDo);
        return systemLogMapper.insertSelective(systemLogDo);
    }

    /**
    * 修改
    * @return
    */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int updateSelective(SystemLogDTO systemLogDto) {
        SystemLogDO systemLogDo = new SystemLogDO();
        BeanUtils.copyProperties(systemLogDto, systemLogDo);
        return systemLogMapper.updateSelective(systemLogDo);
    }

}
