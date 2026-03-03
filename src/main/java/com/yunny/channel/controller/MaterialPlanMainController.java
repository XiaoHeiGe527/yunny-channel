package com.yunny.channel.controller;


import com.yunny.channel.common.dto.MaterialPlanMainDTO;
import com.yunny.channel.common.dto.MaterialPlanSubmitDTO;
import com.yunny.channel.common.interfaces.InsertGroup;
import com.yunny.channel.common.interfaces.UpdateGroup;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.MaterialPlanMainVO;
import com.yunny.channel.common.query.MaterialPlanMainQuery;
import com.yunny.channel.common.vo.SystemUserVO;
import com.yunny.channel.service.MaterialPlanMainService;
import com.yunny.channel.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by fe
 */
@Slf4j
@RestController
@RequestMapping("/materialPlanMain")
public class MaterialPlanMainController {

    @Resource
    private MaterialPlanMainService materialPlanMainService;

    @Autowired
    private SystemUserService systemUserService;

    /**
     * 提交物资计划接口
     */
    @PostMapping("/submit")
    public BaseResult submitPlan(
            @RequestBody @Valid MaterialPlanSubmitDTO submitDTO,
            @RequestAttribute("userNo") String userNo
    ) {
        SystemUserVO systemUserVO = systemUserService.getByUserNo(userNo);

        log.info("接收计划提交请求，申请人：{}", systemUserVO.getName());
        return materialPlanMainService.submitPlan(submitDTO, systemUserVO.getName());
    }




    @RequestMapping("/listByPage")
    public BaseResult<CommonPager<MaterialPlanMainVO>> listByPage(@RequestBody MaterialPlanMainQuery query) {
        query.setPageParameter(new PageParameter(query.getCurrentPage(), query.getPageSize()));
    	CommonPager<MaterialPlanMainVO> commonPager = materialPlanMainService.listByPage(query);
    	return BaseResult.success(commonPager);
    }

    @GetMapping("/getById")
    public BaseResult getById(@RequestParam("id") Long id) {
        return BaseResult.success(materialPlanMainService.getById(id));
    }

    @PostMapping("/create")
    public BaseResult create(@RequestBody @Validated({InsertGroup.class}) MaterialPlanMainDTO materialPlanMainDto) {
        materialPlanMainService.insertSelective(materialPlanMainDto);
        return BaseResult.success();
    }

    @PostMapping("/update")
    public BaseResult update(@RequestBody @Validated({UpdateGroup.class}) MaterialPlanMainDTO materialPlanMainDto) {
        materialPlanMainService.updateSelective(materialPlanMainDto);
        return BaseResult.success();
    }
}
