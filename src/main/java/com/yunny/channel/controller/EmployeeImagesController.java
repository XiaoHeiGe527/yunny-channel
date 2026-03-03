package com.yunny.channel.controller;


import com.yunny.channel.common.dto.EmployeeImagesDTO;
import com.yunny.channel.common.interfaces.InsertGroup;
import com.yunny.channel.common.interfaces.SearchGroup;
import com.yunny.channel.common.interfaces.UpdateGroup;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.EmployeeImagesVO;
import com.yunny.channel.common.query.EmployeeImagesQuery;
import com.yunny.channel.service.EmployeeImagesService;
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
@RequestMapping("/employeeImages")
public class EmployeeImagesController {

    @Resource
    private EmployeeImagesService employeeImagesService;




    @RequestMapping("/listByEmployeeCode")
    public BaseResult<List<EmployeeImagesVO>> listByEmployeeCode(@RequestAttribute("userNo") String userNo,@RequestBody  @Validated({SearchGroup.class}) EmployeeImagesQuery query) {
        query.setUserNo(userNo);
        List<EmployeeImagesVO> positionVOList = employeeImagesService.listByEmployeeCode(query);
        return BaseResult.success(positionVOList);
    }



    @RequestMapping("/listByPage")
    public BaseResult<CommonPager<EmployeeImagesVO>> listByPage(@RequestBody EmployeeImagesQuery query) {
        query.setPageParameter(new PageParameter(query.getCurrentPage(), query.getPageSize()));
    	CommonPager<EmployeeImagesVO> commonPager = employeeImagesService.listByPage(query);
    	return BaseResult.success(commonPager);
    }

    @GetMapping("/getById")
    public BaseResult getById(@RequestParam("id") Long id) {
        return BaseResult.success(employeeImagesService.getById(id));
    }

    @PostMapping("/create")
    public BaseResult create(@RequestBody @Validated({InsertGroup.class}) EmployeeImagesDTO employeeImagesDto) {
        employeeImagesService.insertSelective(employeeImagesDto);
        return BaseResult.success();
    }

    @PostMapping("/update")
    public BaseResult update(@RequestBody @Validated({UpdateGroup.class}) EmployeeImagesDTO employeeImagesDto) {
        employeeImagesService.updateSelective(employeeImagesDto);
        return BaseResult.success();
    }
}
