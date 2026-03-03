package com.yunny.channel.controller;


import com.google.gson.Gson;
import com.yunny.channel.common.constant.ExceptionConstants;
import com.yunny.channel.common.dto.SystemLogDTO;
import com.yunny.channel.common.dto.SystemRoleDTO;
import com.yunny.channel.common.dto.SystemUserDTO;
import com.yunny.channel.common.enums.SystemLogTypeEnum;
import com.yunny.channel.common.interfaces.InsertGroup;
import com.yunny.channel.common.interfaces.SearchGroup;
import com.yunny.channel.common.interfaces.UpdateGroup;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.util.IPUtils;
import com.yunny.channel.common.vo.SystemRoleVO;
import com.yunny.channel.common.query.SystemRoleQuery;
import com.yunny.channel.common.vo.SystemUserVO;
import com.yunny.channel.service.SystemLogService;
import com.yunny.channel.service.SystemRoleService;
import com.yunny.channel.service.SystemUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by fe
 */
@Slf4j
@RestController
@RequestMapping("/systemRole")
public class SystemRoleController {

    @Resource
    private SystemRoleService systemRoleService;

    @Resource
    private SystemLogService systemLogService;

    @Resource
    private SystemUserService systemUserService;

    @RequestMapping("/listByQuery")
    public BaseResult<List<SystemRoleVO>> listByQuery(@RequestBody  @Validated({SearchGroup.class}) SystemRoleQuery query) {
        List<SystemRoleVO> positionVOList = systemRoleService.listByQuery(query);
        return BaseResult.success(positionVOList);
    }

    @RequestMapping("/listByPage")
    public BaseResult<CommonPager<SystemRoleVO>> listByPage(@RequestBody SystemRoleQuery query) {
        query.setPageParameter(new PageParameter(query.getCurrentPage(), query.getPageSize()));
    	CommonPager<SystemRoleVO> commonPager = systemRoleService.listByPage(query);
    	return BaseResult.success(commonPager);
    }

    @GetMapping("/getById")
    public BaseResult getById(@RequestParam("id") Integer id) {
        return BaseResult.success(systemRoleService.getById(id));
    }

    @PostMapping("/create")
    public BaseResult create(@RequestAttribute("userNo") String userNo, HttpServletRequest httpServletRequest,
                             @RequestBody @Validated({InsertGroup.class}) SystemRoleDTO systemRoleDto) {
       int count = systemRoleService.insertSelective(systemRoleDto);

        if(count>0){
            SystemUserVO systemUserVO = systemUserService.getByUserNo(userNo);
            String ip = IPUtils.getIpAddr(httpServletRequest);
            SystemLogDTO systemLogDTO = SystemLogDTO.builder()
                    .operationType(SystemLogTypeEnum.ADD.getCode())
                    .content("删除用户角色:[" + this.toJSON(systemRoleDto) + "]")
                    .userNo(userNo)
                    .createTime(LocalDateTime.now())
                    .url("/systemRole/create")
                    .ip(ip)
                    .operatorName(systemUserVO.getName())
                    .remarks("删除用户角色")
                    .build();
            systemLogService.insertSelective(systemLogDTO);
        }

        return BaseResult.success();
    }

    @PostMapping("/update")
    public BaseResult update(@RequestAttribute("userNo") String userNo,
                             @RequestBody @Validated({UpdateGroup.class}) SystemRoleDTO systemRoleDto, HttpServletRequest httpServletRequest) {
        if(systemRoleDto.getId()==1){

            return BaseResult.failure(500,"超级管理员角色不可修改！");
        }
      int count =  systemRoleService.updateSelective(systemRoleDto);
        if(count>0){
            SystemUserVO systemUserVO = systemUserService.getByUserNo(userNo);
            String ip = IPUtils.getIpAddr(httpServletRequest);
            SystemLogDTO systemLogDTO = SystemLogDTO.builder()
                    .operationType(SystemLogTypeEnum.ADD.getCode())
                    .content("删除用户角色:[" + this.toJSON(systemRoleDto) + "]")
                    .userNo(userNo)
                    .createTime(LocalDateTime.now())
                    .url("/systemRole/update")
                    .ip(ip)
                    .operatorName(systemUserVO.getName())
                    .remarks("删除用户角色")
                    .build();
            systemLogService.insertSelective(systemLogDTO);
        }

        return BaseResult.success();
    }


    @PostMapping("/deleteById")
    public BaseResult deleteById(@RequestAttribute("userNo") String userNo,@RequestBody @Validated({UpdateGroup.class})
            SystemRoleDTO systemRoleDto, HttpServletRequest httpServletRequest) {

        BaseResult result = BaseResult.success( systemRoleService.deleteById(systemRoleDto.getId()));

        if(result.getCode()== ExceptionConstants.RESULT_CODE_SUCCESS){
            SystemUserVO systemUserVO = systemUserService.getByUserNo(userNo);
            String ip = IPUtils.getIpAddr(httpServletRequest);
            SystemLogDTO systemLogDTO = SystemLogDTO.builder()
                    .operationType(SystemLogTypeEnum.ADD.getCode())
                    .content("删除用户角色:[" + this.toJSON(systemRoleDto) + "]")
                    .userNo(userNo)
                    .createTime(LocalDateTime.now())
                    .url("/systemRole/deleteById")
                    .ip(ip)
                    .operatorName(systemUserVO.getName())
                    .remarks("删除用户角色")
                    .build();
            systemLogService.insertSelective(systemLogDTO);
        }


        return result;


    }


    private String toJSON(SystemRoleDTO systemRoleDto) {
        Gson gson = new Gson();
        return gson.toJson(systemRoleDto);
    }
}
