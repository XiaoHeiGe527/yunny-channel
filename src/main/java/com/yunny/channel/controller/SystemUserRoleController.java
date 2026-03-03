package com.yunny.channel.common.controller;


import com.yunny.channel.common.dto.SystemUserRoleDTO;
import com.yunny.channel.common.interfaces.InsertGroup;
import com.yunny.channel.common.interfaces.UpdateGroup;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.SystemUserRoleVO;
import com.yunny.channel.common.query.SystemUserRoleQuery;
import com.yunny.channel.service.SystemRoleService;
import com.yunny.channel.service.SystemUserRoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by fe
 */
@Slf4j
@RestController
@RequestMapping("/systemUserRole")
public class SystemUserRoleController {

    @Resource
    private SystemUserRoleService systemUserRoleService;

    @Resource
    SystemRoleService systemRoleService;

    @RequestMapping("/listByPage")
    public BaseResult<CommonPager<SystemUserRoleVO>> listByPage(@RequestBody SystemUserRoleQuery query) {
        query.setPageParameter(new PageParameter(query.getCurrentPage(), query.getPageSize()));
    	CommonPager<SystemUserRoleVO> commonPager = systemUserRoleService.listByPage(query);
    	return BaseResult.success(commonPager);
    }

    @GetMapping("/getByUserNo")
    public BaseResult getByUserNo(@RequestParam("userNo") String userNo) {

        SystemUserRoleVO systemUserRoleVO = systemUserRoleService.getByUserNo(userNo);
        if(systemUserRoleVO!=null){
            return  BaseResult.success(systemUserRoleVO);
        }

            return BaseResult.success(SystemUserRoleVO.builder().roleId(9).userNo(userNo).build());
    }

    @PostMapping("/create")
    public BaseResult create(@RequestBody @Validated({InsertGroup.class}) SystemUserRoleDTO systemUserRoleDto) {
        systemUserRoleService.insertSelective(systemUserRoleDto);
        return BaseResult.success();
    }

    @PostMapping("/update")
    public BaseResult update(@RequestBody @Validated({UpdateGroup.class}) SystemUserRoleDTO systemUserRoleDto) {
        systemUserRoleService.updateSelective(systemUserRoleDto);
        return BaseResult.success();
    }
}
