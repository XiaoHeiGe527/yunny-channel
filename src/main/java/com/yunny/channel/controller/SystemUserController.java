package com.yunny.channel.controller;


import com.google.gson.Gson;
import com.yunny.channel.common.constant.ExceptionConstants;
import com.yunny.channel.common.dto.SystemLogDTO;
import com.yunny.channel.common.dto.SystemUserDTO;
import com.yunny.channel.common.enums.SystemLogTypeEnum;
import com.yunny.channel.common.interfaces.InsertGroup;
import com.yunny.channel.common.interfaces.UpdateGroup;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.util.IPUtils;
import com.yunny.channel.common.vo.SystemUserVO;
import com.yunny.channel.common.query.SystemUserQuery;
import com.yunny.channel.service.SystemLogService;
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
@RequestMapping("/systemUser")
public class SystemUserController {

    @Resource
    private SystemUserService systemUserService;

    @Resource
    private SystemLogService systemLogService;

    @RequestMapping("/listByPage")
    public BaseResult<CommonPager<SystemUserVO>> listByPage(@RequestBody SystemUserQuery query) {
        query.setPageParameter(new PageParameter(query.getCurrentPage(), query.getPageSize()));
    	CommonPager<SystemUserVO> commonPager = systemUserService.listByPage(query);
    	return BaseResult.success(commonPager);
    }

    @GetMapping("/getById")
    public BaseResult getById(@RequestParam("id") Long id) {
        return BaseResult.success(systemUserService.getById(id));
    }

    @PostMapping("/create")
    public BaseResult create(@RequestAttribute("userNo") String userNo
            , @RequestBody @Validated({InsertGroup.class}) SystemUserDTO systemUserDto, HttpServletRequest httpServletRequest) {

        BaseResult result =  systemUserService.insertSelective(systemUserDto);

        if(result.getCode()== ExceptionConstants.RESULT_CODE_SUCCESS){
            SystemUserVO systemUserVO = systemUserService.getByUserNo(userNo);
            String ip = IPUtils.getIpAddr(httpServletRequest);
            SystemLogDTO systemLogDTO = SystemLogDTO.builder()
                    .operationType(SystemLogTypeEnum.ADD.getCode())
                    .content("创建系统用户:[" + this.toJSON(systemUserDto) + "]")
                    .userNo(userNo)
                    .createTime(LocalDateTime.now())
                    .url("/systemUser/create")
                    .ip(ip)
                    .operatorName(systemUserVO.getName())
                    .remarks("创建系统用户")
                    .build();
            systemLogService.insertSelective(systemLogDTO);
        }

        return result;
    }


    @PostMapping("/update")
    public BaseResult update(@RequestAttribute("userNo") String userNo
            ,@RequestBody @Validated({UpdateGroup.class}) SystemUserDTO systemUserDto
            , HttpServletRequest httpServletRequest) {

        if(systemUserDto.getUserNo().equals("su20200409000001")){

            return BaseResult.failure(500,"超级管理员账号不可修改！");
        }

        int count = systemUserService.updateSelective(systemUserDto);

        if(count>0){
            SystemUserVO systemUserVO = systemUserService.getByUserNo(userNo);
            String ip = IPUtils.getIpAddr(httpServletRequest);
            SystemLogDTO systemLogDTO = SystemLogDTO.builder()
                    .operationType(SystemLogTypeEnum.ADD.getCode())
                    .content("修改系统用户:[" + this.toJSON(systemUserDto) + "]")
                    .userNo(userNo)
                    .createTime(LocalDateTime.now())
                    .url("/systemUser/update")
                    .ip(ip)
                    .operatorName(systemUserVO.getName())
                    .remarks("修改系统用户")
                    .build();
            systemLogService.insertSelective(systemLogDTO);
        }

        return BaseResult.success();
    }


    private String toJSON(SystemUserDTO systemUserDTO) {
        Gson gson = new Gson();
        return gson.toJson(systemUserDTO);
    }
}
