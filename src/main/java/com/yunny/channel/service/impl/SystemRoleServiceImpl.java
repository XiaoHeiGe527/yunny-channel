package com.yunny.channel.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.yunny.channel.common.entity.SystemRoleDO;
import com.yunny.channel.common.dto.SystemRoleDTO;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.SystemRoleVO;
import com.yunny.channel.common.query.SystemRoleQuery;
import com.yunny.channel.common.vo.SystemUserVO;
import com.yunny.channel.mapper.SystemRoleMapper;
import com.yunny.channel.service.SystemRoleResourceService;
import com.yunny.channel.service.SystemRoleService;
import com.yunny.channel.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.time.LocalDateTime;
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
public class SystemRoleServiceImpl implements SystemRoleService {

    @Resource
    private SystemRoleMapper systemRoleMapper;


    @Autowired
    SystemRoleResourceService systemRoleResourceService;

    @Autowired
    SystemUserService systemUserService;


    /**
    * 分页查询
    * @return
    */
    @Override
    public CommonPager<SystemRoleVO> listByPage(SystemRoleQuery systemRoleQuery) {
        PageParameter pageParameter = systemRoleQuery.getPageParameter();
        return new CommonPager<SystemRoleVO>(
                new PageParameter(pageParameter.getCurrentPage(), pageParameter.getPageSize(),
                        systemRoleMapper.countByQuery(systemRoleQuery)),
                systemRoleMapper.listByQuery(systemRoleQuery).stream()
                        .map(item -> {
                            SystemRoleVO systemRoleVo = new SystemRoleVO();
                            BeanUtils.copyProperties(item, systemRoleVo);
                            return systemRoleVo;
                        }).collect(Collectors.toList()));
    }

    @Override
    public List<SystemRoleVO> listByQuery(SystemRoleQuery query) {
        // 非岗位数据直接从数据库获取
        List<SystemRoleDO> doList = systemRoleMapper.listByQuery(query);
        return doList.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

    }


    private SystemRoleVO convertToVO(SystemRoleDO systemRoleDO) {
        return SystemRoleVO.builder()
                .id(systemRoleDO.getId())
                .content(systemRoleDO.getContent())
                .name(systemRoleDO.getName())
                .type(systemRoleDO.getType())
                .state(systemRoleDO.getState())
                .build();
    }


    /**
    * 通过id获取
    * @return
    */
    @Override
    public SystemRoleVO getById(Integer id) {
    	SystemRoleDO systemRoleDo = systemRoleMapper.getById(id);
    	if(null == systemRoleDo){
    		return null;
    	}
    	SystemRoleVO systemRoleVo = new SystemRoleVO();
    	BeanUtils.copyProperties(systemRoleDo, systemRoleVo);
    	return systemRoleVo;
    }

    /**
    * 新增
    * @return
    */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int insertSelective(SystemRoleDTO systemRoleDto) {
        SystemRoleDO systemRoleDo = new SystemRoleDO();
        BeanUtils.copyProperties(systemRoleDto, systemRoleDo);
        return systemRoleMapper.insertSelective(systemRoleDo);
    }

    /**
    * 修改
    * @return
    */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int updateSelective(SystemRoleDTO systemRoleDto) {
        SystemRoleDO systemRoleDo = new SystemRoleDO();
        systemRoleDo.setType( systemRoleDto.getType()==null?0:1);
        systemRoleDo.setId(systemRoleDto.getId());
        systemRoleDo.setName(systemRoleDto.getName());
        systemRoleDo.setContent(systemRoleDto.getContent());
        systemRoleDo.setState(systemRoleDto.getState());
        systemRoleDo.setUpdateTime(LocalDateTime.now());
      //  BeanUtils.copyProperties(systemRoleDto, systemRoleDo);
        return systemRoleMapper.updateSelective(systemRoleDo);
    }


    @Override
    @Transactional
    public BaseResult deleteById(Integer id) {

        SystemRoleVO systemRoleVO = this.getById(id);

        if(systemRoleVO==null){
            return BaseResult.failure(500, "错误的角色ID");
        }

        List<SystemUserVO> systemUserVOList = systemUserService.getUsersByRoleId(id);
        if (CollectionUtil.isNotEmpty(systemUserVOList)) {
            // 拼接格式：登录名(姓名)，多个用户用逗号分隔
            String msg = systemUserVOList.stream()
                    .map(vo -> vo.getLoginName() + "(" + vo.getName() + ")")
                    .collect(Collectors.joining(","));
            return BaseResult.failure(500, "删除失败,当前角色存在绑定用户：【" + msg + "】");
        }

        systemRoleResourceService.deleteByRoleId(id);
        systemRoleMapper.deleteById(id);


        return BaseResult.success();
    }

}
