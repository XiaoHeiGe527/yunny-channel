package com.yunny.channel.controller;

import com.yunny.channel.common.dto.VehicleCustomersDTO;
import com.yunny.channel.common.interfaces.InsertGroup;
import com.yunny.channel.common.interfaces.UpdateGroup;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.VehicleCustomersVO;
import com.yunny.channel.common.query.VehicleCustomersQuery;
import com.yunny.channel.service.VehicleCustomersService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

/**
 * Created by fe
 */
@Slf4j
@RestController
@RequestMapping("/vehicleCustomers")
public class VehicleCustomersController {

    @Resource
    private VehicleCustomersService vehicleCustomersService;


    @RequestMapping("/listAll")
    public BaseResult<Set<String>> listAll(@RequestBody VehicleCustomersQuery query) {
        return BaseResult.success(vehicleCustomersService.fuzzyMatchingVehicleCustomers(query.getName()));
    }


//    @RequestMapping("/listByPage")
//    public BaseResult<CommonPager<VehicleCustomersVO>> listByPage(@RequestBody VehicleCustomersQuery query) {
//        query.setPageParameter(new PageParameter(query.getCurrentPage(), query.getPageSize()));
//    	CommonPager<VehicleCustomersVO> commonPager = vehicleCustomersService.listByPage(query);
//    	return BaseResult.success(commonPager);
//    }
//
//    @GetMapping("/getById")
//    public BaseResult getById(@RequestParam("id") Integer id) {
//        return BaseResult.success(vehicleCustomersService.getById(id));
//    }
//
//    @PostMapping("/create")
//    public BaseResult create(@RequestBody @Validated({InsertGroup.class}) VehicleCustomersDTO vehicleCustomersDto) {
//        vehicleCustomersService.insertSelective(vehicleCustomersDto);
//        return BaseResult.success();
//    }
//
//    @PostMapping("/update")
//    public BaseResult update(@RequestBody @Validated({UpdateGroup.class}) VehicleCustomersDTO vehicleCustomersDto) {
//        vehicleCustomersService.updateSelective(vehicleCustomersDto);
//        return BaseResult.success();
//    }



}
