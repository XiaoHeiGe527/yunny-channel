package com.yunny.channel.controller;

import com.google.gson.Gson;
import com.yunny.channel.common.dto.CompanyVehiclesDTO;
import com.yunny.channel.common.dto.SystemLogDTO;
import com.yunny.channel.common.enums.SystemLogTypeEnum;
import com.yunny.channel.common.interfaces.InsertGroup;
import com.yunny.channel.common.interfaces.UpdateGroup;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.util.IPUtils;
import com.yunny.channel.common.vo.CompanyVehiclesVO;
import com.yunny.channel.common.query.CompanyVehiclesQuery;
import com.yunny.channel.common.vo.SystemUserVO;
import com.yunny.channel.service.CompanyVehiclesService;
import com.yunny.channel.service.SystemLogService;
import com.yunny.channel.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/companyVehicles")
public class CompanyVehiclesController {

    @Resource
    private CompanyVehiclesService companyVehiclesService;


    @Resource
    private SystemLogService systemLogService;


    @Autowired
    private SystemUserService systemUserService;

    @RequestMapping("/listByPage")
    public BaseResult<Map<String, Object>> listByPage(@RequestBody CompanyVehiclesQuery query) {
        query.setPageParameter(new PageParameter(query.getCurrentPage(), query.getPageSize()));
        CommonPager<CompanyVehiclesVO> commonPager = companyVehiclesService.listByPage(query);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("commonPager", commonPager);
        resultMap.put("额外数据", "额外数据");
        return BaseResult.success(resultMap);
    }


    /**
     * 可用且车队管理的车辆
     * @return
     */
    @RequestMapping("/canDriveList")
    public BaseResult<List<CompanyVehiclesVO>> canDriveList() {
        return BaseResult.success(companyVehiclesService.listAllByQuery());
    }



    /**
     * 还车
     * @param companyVehiclesDto
     * @return
     */
   @PostMapping("/returnCompanyVehicles")
    public BaseResult returnCompanyVehicles(@RequestBody @Validated({UpdateGroup.class}) CompanyVehiclesDTO companyVehiclesDto
           , HttpServletRequest httpServletRequest, @RequestAttribute("userNo") String userNo) {
       int count = companyVehiclesService.returnCompanyVehicles(companyVehiclesDto);
       if(count > 0){
           SystemUserVO systemUserVO = systemUserService.getByUserNo(userNo);
           String ip = IPUtils.getIpAddr(httpServletRequest);
           SystemLogDTO systemLogDTO = SystemLogDTO.builder()
                   .operationType(SystemLogTypeEnum.UPDATE.getCode())
                   .content(this.toJSON(companyVehiclesDto))
                   .userNo(userNo)
                   .url("/companyVehicles/returnCompanyVehicles")
                   .createTime(LocalDateTime.now())
                   .ip(ip)
                   .operatorName(systemUserVO.getName())
                   .remarks("车辆还车:[" + companyVehiclesDto.getCarNumber() + "]")
                   .build();
           systemLogService.insertSelective(systemLogDTO);

       }

        return BaseResult.success();
    }

    private String toJSON(CompanyVehiclesDTO companyVehiclesDTO) {
        Gson gson = new Gson();
        return gson.toJson(companyVehiclesDTO);
    }


    @GetMapping("/getById")
    public BaseResult getById(@RequestParam("id") Long id) {
        return BaseResult.success(companyVehiclesService.getById(id));
    }

//    @PostMapping("/create")
//    public BaseResult create(@RequestBody @Validated({InsertGroup.class}) CompanyVehiclesDTO companyVehiclesDto) {
//        companyVehiclesService.insertSelective(companyVehiclesDto);
//        return BaseResult.success();
//    }
}
