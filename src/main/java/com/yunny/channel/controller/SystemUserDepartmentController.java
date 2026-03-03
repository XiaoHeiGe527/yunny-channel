package com.yunny.channel.controller;

import com.yunny.channel.common.dto.SystemUserDepartmentDTO;
import com.yunny.channel.common.interfaces.InsertGroup;
import com.yunny.channel.common.interfaces.SearchGroup;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.SystemUserDepartmentVO;
import com.yunny.channel.common.query.SystemUserDepartmentQuery;
import com.yunny.channel.service.SystemUserDepartmentService;
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
@RequestMapping("/systemUserDepartment")
public class SystemUserDepartmentController {

    @Resource
    private SystemUserDepartmentService systemUserDepartmentService;


    @RequestMapping("/getUserDepartment")
    public BaseResult<List<SystemUserDepartmentVO>> getUserDepartment(@RequestBody  @Validated({SearchGroup.class}) SystemUserDepartmentQuery query) {
        return BaseResult.success(systemUserDepartmentService.getUserDepartment(query));
    }


//    @RequestMapping("/listByPage")
//    public BaseResult<CommonPager<SystemUserDepartmentVO>> listByPage(@RequestBody SystemUserDepartmentQuery query) {
//        query.setPageParameter(new PageParameter(query.getCurrentPage(), query.getPageSize()));
//    	CommonPager<SystemUserDepartmentVO> commonPager = systemUserDepartmentService.listByPage(query);
//    	return BaseResult.success(commonPager);
//    }
//
//    @GetMapping("/getById")
//    public BaseResult getById(@RequestParam("id") Long id) {
//        return BaseResult.success(systemUserDepartmentService.getById(id));
//    }
//
//    @PostMapping("/create")
//    public BaseResult create(@RequestBody @Validated({InsertGroup.class}) SystemUserDepartmentDTO systemUserDepartmentDto) {
//        systemUserDepartmentService.insertSelective(systemUserDepartmentDto);
//        return BaseResult.success();
//    }
//
//    @PostMapping("/update")
//    public BaseResult update(@RequestBody @Validated({UpdateGroup.class}) SystemUserDepartmentDTO systemUserDepartmentDto) {
//        systemUserDepartmentService.updateSelective(systemUserDepartmentDto);
//        return BaseResult.success();
//    }
}
