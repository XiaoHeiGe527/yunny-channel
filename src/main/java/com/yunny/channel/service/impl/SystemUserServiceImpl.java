package com.yunny.channel.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.yunny.channel.common.dto.SystemUserRoleDTO;
import com.yunny.channel.common.entity.SystemUserDO;
import com.yunny.channel.common.dto.SystemUserDTO;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.query.LoginUserQuery;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.util.MD5;
import com.yunny.channel.common.vo.SystemUserVO;
import com.yunny.channel.common.query.SystemUserQuery;
import com.yunny.channel.mapper.SystemUserMapper;
import com.yunny.channel.service.SystemUserRoleService;
import com.yunny.channel.service.SystemUserService;

import com.yunny.channel.util.NumberUtils;
import com.yunny.channel.util.OrderInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.stream.Collectors;

import static com.yunny.channel.common.enums.UserCodeEnum.SYSTEM_USER_ALREADY_EXISTS;

/**
 * Created by Fe
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class SystemUserServiceImpl
        implements SystemUserService {

    @Resource
    private SystemUserMapper systemUserMapper;

    @Autowired
    SystemUserRoleService systemUserRoleService;


    @Autowired
    OrderInfoUtil orderInfoUtil;

    /**
     * 分页查询
     *
     * @return
     */
    @Override
    public CommonPager<SystemUserVO> listByPage(SystemUserQuery systemUserQuery) {
        PageParameter pageParameter = systemUserQuery.getPageParameter();
        return new CommonPager<SystemUserVO>(
                new PageParameter(pageParameter.getCurrentPage(), pageParameter.getPageSize(),
                        systemUserMapper.countByQuery(systemUserQuery)),
                systemUserMapper.listByQuery(systemUserQuery).stream().map(item -> {
                    SystemUserVO systemUserVo = new SystemUserVO();
                    BeanUtils.copyProperties(item, systemUserVo);
                    return systemUserVo;
                }).collect(Collectors.toList()));
    }

    /**
     * 通过id获取
     *
     * @return
     */
    @Override
    public SystemUserVO getById(Long id) {
        SystemUserDO systemUserDo = systemUserMapper.getById(id);
        if (null == systemUserDo) {
            return null;
        }
        SystemUserVO systemUserVo = new SystemUserVO();
        BeanUtils.copyProperties(systemUserDo, systemUserVo);
        return systemUserVo;
    }

    @Override
    public SystemUserVO getByUserNo(String userNo) {

        List<SystemUserDO> systemUserDOList = systemUserMapper.getByUserNo(userNo);
        if (systemUserDOList == null || systemUserDOList.size() == 0) {
            return null;
        }
        SystemUserDO systemUserDOdo = systemUserDOList.get(0);
        SystemUserVO vo = new SystemUserVO();
        vo.setId(systemUserDOdo.getId());
        vo.setUserNo(systemUserDOdo.getUserNo());
        vo.setLoginName(systemUserDOdo.getLoginName());
        vo.setName(systemUserDOdo.getName());
        vo.setPassword(systemUserDOdo.getPassword());
        vo.setMobile(systemUserDOdo.getMobile());
        vo.setCreateTime(systemUserDOdo.getCreateTime());
        vo.setUpdateTime(systemUserDOdo.getUpdateTime());
        vo.setState(systemUserDOdo.getState());
        vo.setAge(systemUserDOdo.getAge());
        vo.setCity(systemUserDOdo.getCity());
        vo.setEmail(systemUserDOdo.getEmail());
        return vo;

    }

    /**
     * 新增
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResult insertSelective(SystemUserDTO systemUserDto) {

        SystemUserQuery systemUserQuery=new SystemUserQuery();
        systemUserQuery.setLoginName(systemUserDto.getLoginName());

        List<SystemUserDO> systemUserDOList=systemUserMapper.selectByPrimaryKey(systemUserQuery);

        if(!systemUserDOList.isEmpty()){
            return BaseResult.failure(SYSTEM_USER_ALREADY_EXISTS.getCode(),SYSTEM_USER_ALREADY_EXISTS.getMessage());
        }

        SystemUserDO systemUserDo = new SystemUserDO();
        systemUserDo.setLoginName(systemUserDto.getLoginName());
        systemUserDo.setMobile(systemUserDto.getMobile());
        systemUserDo.setName(systemUserDto.getName());
        systemUserDo.setPassword(MD5.md5(systemUserDto.getPassword()));
        systemUserDo.setUserNo(orderInfoUtil.getUserNo());
        systemUserDo.setEmail(systemUserDto.getEmail());
        systemUserDo.setCity(systemUserDto.getCity());
        systemUserDo.setCreateTime(LocalDateTime.now());
        systemUserDo.setUpdateTime(LocalDateTime.now());
        systemUserDo.setAge(systemUserDto.getAge());

       int count = systemUserMapper.insertSelective(systemUserDo);

        // 绑定用户角色
        SystemUserRoleDTO systemUserRoleDto = new SystemUserRoleDTO();
        systemUserRoleDto.setRoleId(systemUserDto.getRoleId());
        systemUserRoleDto.setUserNo(systemUserDo.getUserNo());
        systemUserRoleService.insertSelective(systemUserRoleDto);

        return BaseResult.success(count);
    }

    /**
     * 修改
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int updateSelective(SystemUserDTO systemUserDto) {
        List<SystemUserDO> userList =  systemUserMapper.getByUserNo(systemUserDto.getUserNo());
        if(CollectionUtil.isEmpty(userList)){

            return 0;
        }

        SystemUserDO systemUserDo = userList.get(0);
        systemUserDo.setName(systemUserDto.getName());
        systemUserDo.setState(systemUserDto.getState());
        BeanUtils.copyProperties(systemUserDto, systemUserDo);

        SystemUserRoleDTO systemUserRoleDto = new SystemUserRoleDTO();
        systemUserRoleDto.setUserNo(systemUserDto.getUserNo());
        systemUserRoleDto.setRoleId(systemUserDto.getRoleId());
        systemUserRoleService.deleteInsert(systemUserRoleDto);

        return systemUserMapper.updateByUserNo(systemUserDo);
    }

    @Override
    public List<SystemUserVO> queryUserBaseInfoDOList(LoginUserQuery query) {
        List<SystemUserDO> ubiDoList = systemUserMapper.querySystemUserDOList(query);

        List<SystemUserVO> ubiVoList = new ArrayList<>();
        if (CollectionUtils.isEmpty(ubiDoList)) {
            return null;
        }

        ubiDoList.forEach(p -> {
            SystemUserVO vo = new SystemUserVO();
            vo.setMobile(p.getMobile());
            vo.setPassword(p.getPassword());
            vo.setState(p.getState());
            vo.setUserNo(p.getUserNo());
            vo.setLoginName(p.getLoginName());
            vo.setName(p.getName());
            vo.setId(p.getId());
            ubiVoList.add(vo);
        });

        return ubiVoList;
    }

    @Override
    public BaseResult changePassword(SystemUserQuery systemUserQuery) {
        SystemUserVO vo = this.getByUserNo(systemUserQuery.getUserNo());
        if (vo == null) {
            return BaseResult.failure(500, "密码修改失败！");
        }
        if(!MD5.md5(systemUserQuery.getOldPassword()).equals(vo.getPassword())){
            return BaseResult.failure(500, "新旧密码不一致！");
        }

        String newpass =  MD5.md5(systemUserQuery.getPassword());
        log.info("新密码：[{}],MD5：[{}]",systemUserQuery.getPassword(),newpass);
        SystemUserDTO systemUserDto = SystemUserDTO.builder()
                .id(vo.getId())
                .password(newpass)
                .build();
        return BaseResult.success(this.updateSelective(systemUserDto));
    }

    @Override
    public List<SystemUserVO> getUsersByRoleId(Integer roleId) {
        return   systemUserMapper.getUsersByRoleId(roleId).stream().map(item -> {
            SystemUserVO systemUserVo = new SystemUserVO();
            BeanUtils.copyProperties(item, systemUserVo);
            return systemUserVo;
        }).collect(Collectors.toList());
    }

}
