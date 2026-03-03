package com.yunny.channel.controller;

import com.yunny.channel.common.dto.MaterialPlanDetailDTO;
import com.yunny.channel.common.interfaces.InsertGroup;
import com.yunny.channel.common.interfaces.UpdateGroup;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.MaterialPlanDetailVO;
import com.yunny.channel.common.query.MaterialPlanDetailQuery;
import com.yunny.channel.service.MaterialPlanDetailService;
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
@RequestMapping("/materialPlanDetail")
public class MaterialPlanDetailController {

    @Resource
    private MaterialPlanDetailService materialPlanDetailService;

    @RequestMapping("/listByPage")
    public BaseResult<CommonPager<MaterialPlanDetailVO>> listByPage(@RequestBody MaterialPlanDetailQuery query) {
        query.setPageParameter(new PageParameter(query.getCurrentPage(), query.getPageSize()));
    	CommonPager<MaterialPlanDetailVO> commonPager = materialPlanDetailService.listByPage(query);
    	return BaseResult.success(commonPager);
    }

    @GetMapping("/getById")
    public BaseResult getById(@RequestParam("id") Long id) {
        return BaseResult.success(materialPlanDetailService.getById(id));
    }

    @PostMapping("/create")
    public BaseResult create(@RequestBody @Validated({InsertGroup.class}) MaterialPlanDetailDTO materialPlanDetailDto) {
        materialPlanDetailService.insertSelective(materialPlanDetailDto);
        return BaseResult.success();
    }

    @PostMapping("/update")
    public BaseResult update(@RequestBody @Validated({UpdateGroup.class}) MaterialPlanDetailDTO materialPlanDetailDto) {
        materialPlanDetailService.updateSelective(materialPlanDetailDto);
        return BaseResult.success();
    }
}
