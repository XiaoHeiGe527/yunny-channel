package com.yunny.channel.controller;

import com.google.gson.Gson;
import com.yunny.channel.common.dto.SystemLogDTO;
import com.yunny.channel.common.dto.VehicleInsuranceDTO;
import com.yunny.channel.common.enums.SystemLogTypeEnum;
import com.yunny.channel.common.interfaces.InsertGroup;
import com.yunny.channel.common.interfaces.UpdateGroup;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.util.IPUtils;
import com.yunny.channel.common.vo.SystemUserVO;
import com.yunny.channel.common.vo.VehicleInsuranceVO;
import com.yunny.channel.common.query.VehicleInsuranceQuery;
import com.yunny.channel.service.SystemLogService;
import com.yunny.channel.service.SystemUserService;
import com.yunny.channel.service.VehicleInsuranceService;
import com.yunny.channel.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 车辆续保相关接口
 */
@Slf4j
@RestController
@RequestMapping("/vehicleInsurance")
public class VehicleInsuranceController {

    @Resource
    private VehicleInsuranceService vehicleInsuranceService;

    @Resource
    private SystemLogService systemLogService;


    @Autowired
    private SystemUserService systemUserService;

    /**
     * 查询到期车辆
     *
     * @param query
     * @return
     */
    @RequestMapping("/listByExpirationReminder")
    public BaseResult<Map<String, Object>> listByExpirationReminder(@RequestBody VehicleInsuranceQuery query) {
        log.info("调用接口-----------------");
        List<VehicleInsuranceVO> list = vehicleInsuranceService.listByExpirationReminder(query);
        BigDecimal sum = this.getSumTotal(list);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", list);
        resultMap.put("sum", sum);
        return BaseResult.success(resultMap);
    }

    @RequestMapping("/listAll")
    public BaseResult<Map<String, Object>> listAll(@RequestBody VehicleInsuranceQuery query) {
        log.info("调用接口----------查询所有-------");
        List<VehicleInsuranceVO> list = vehicleInsuranceService.listAll(query);
        BigDecimal sum = this.getSumTotal(list);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", list);
        resultMap.put("sum", sum);
        return BaseResult.success(resultMap);
    }

    /**
     * 批量插入车数据
     *
     * @param dtoList
     * @return
     */
    @PostMapping("/batchInsert")
    public BaseResult batchInsert(@RequestBody @Validated({InsertGroup.class}) List<VehicleInsuranceDTO> dtoList
            , @RequestAttribute("userNo") String userNo,HttpServletRequest httpServletRequest) {
        if (dtoList.size() == 0 || dtoList.size() > 50) {
            return BaseResult.failure(500, "不能超过50条！");
        }

        Map<String,String> resultMap = vehicleInsuranceService.batchInsert(dtoList, userNo);
       Integer count =  Integer.valueOf(resultMap.get("count"));
                if(count>0){
            SystemUserVO systemUserVO = systemUserService.getByUserNo(userNo);
            String ip = IPUtils.getIpAddr(httpServletRequest);
            SystemLogDTO systemLogDTO = SystemLogDTO.builder()
                    .operationType(SystemLogTypeEnum.ADD.getCode())
                    .content(resultMap.get("batch"))
                    .userNo(userNo)
                    .createTime(LocalDateTime.now())
                    .url("/vehicleInsurance/batchInsert")
                    .ip(ip)
                    .operatorName(systemUserVO.getName())
                    .remarks("批量插入承保车辆数据:[" + count + "]条，批次号是："+resultMap.get("batch"))
                    .build();
            systemLogService.insertSelective(systemLogDTO);
        }


        return BaseResult.success();
    }

    /**
     * 车辆续保
     *
     * @param vehicleInsuranceQuery
     * @return
     */
    @PostMapping("/renewInsurance")
    public BaseResult renewInsurance(@RequestBody @Validated({UpdateGroup.class}) VehicleInsuranceQuery vehicleInsuranceQuery,
                                     @RequestAttribute("userNo") String userNo,HttpServletRequest httpServletRequest) {
        Integer count = vehicleInsuranceService.renewInsurance(vehicleInsuranceQuery, userNo);
        if (count == 1) {
            SystemUserVO systemUserVO = systemUserService.getByUserNo(userNo);
            String ip = IPUtils.getIpAddr(httpServletRequest);
            SystemLogDTO systemLogDTO = SystemLogDTO.builder()
                    .operationType(SystemLogTypeEnum.UPDATE.getCode())
                    .content(this.queryToJSON(vehicleInsuranceQuery))
                    .userNo(userNo)
                    .url("/vehicleInsurance/renewInsurance")
                    .createTime(LocalDateTime.now())
                    .remarks("车辆续保处理记录:[" + count + "]条，记录ID是：[" + vehicleInsuranceQuery.getId() + "]")
                    .ip(ip)
                    .operatorName(systemUserVO.getName())
                    .build();
            systemLogService.insertSelective(systemLogDTO);

            return BaseResult.success();
        }
        return BaseResult.failure(500, "续保记录错误！请联系管理员" + count);
    }

    @RequestMapping("/listByPage")
    public BaseResult<Map<String, Object>> listByPage(@RequestBody VehicleInsuranceQuery query) {
        query.setPageParameter(new PageParameter(query.getCurrentPage(), query.getPageSize()));
        CommonPager<VehicleInsuranceVO> commonPager = vehicleInsuranceService.listByPage(query);
        BigDecimal sum = this.getSumTotal(commonPager.getDataList());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("commonPager", commonPager);
        resultMap.put("sum", sum);
        return BaseResult.success(resultMap);
    }


    private BigDecimal getSumTotal(List<VehicleInsuranceVO> list) {

        BigDecimal sum = new BigDecimal("0");
        for (VehicleInsuranceVO vo : list) {
            if (StringUtil.isEmpty(vo.getTotal()) || vo.getTotal().equals("0")) {
                continue;
            }
            BigDecimal voTotal = new BigDecimal(vo.getTotal());
            sum = sum.add(voTotal);
        }

        //  log.info("列表统计金额：[{}]",sum);

        return sum;

    }

    /**
     * 导入车辆Excel
     *
     * @return
     */
    @PostMapping("/importVehicleInsuranceExcel")
    public BaseResult importVehicleInsuranceExcel(@NotNull MultipartFile file, @RequestAttribute("userNo") String userNo
            ,HttpServletRequest httpServletRequest) {
        Map<String,String> resultMap = vehicleInsuranceService.importVehicleInsuranceExcel(file);
        Integer count =  Integer.valueOf(resultMap.get("count"));
        if(count>0){
            SystemUserVO systemUserVO = systemUserService.getByUserNo(userNo);
            String ip = IPUtils.getIpAddr(httpServletRequest);
            SystemLogDTO systemLogDTO = SystemLogDTO.builder()
                    .operationType(SystemLogTypeEnum.UPDATE.getCode())
                    .content(resultMap.get("batch"))
                    .userNo(userNo)
                    .url("/vehicleInsurance/importVehicleInsuranceExcel")
                    .createTime(LocalDateTime.now())
                    .ip(ip)
                    .operatorName(systemUserVO.getName())
                    .remarks("车辆导入记录:[" + count + "]条，批次号是："+resultMap.get("batch"))
                    .build();
            systemLogService.insertSelective(systemLogDTO);
        }

        return BaseResult.success(count);
    }

    private String toJSON(VehicleInsuranceDTO vehicleInsuranceDTO) {
        Gson gson = new Gson();
        return gson.toJson(vehicleInsuranceDTO);
    }

        private String queryToJSON(VehicleInsuranceQuery query){
            Gson gson = new Gson();
            return gson.toJson(query);
        }

        //    @GetMapping("/getById")
        //    public BaseResult getById(@RequestParam("id") Long id) {
        //        return BaseResult.success(vehicleInsuranceService.getById(id));
        //    }

        //    /**
        //     * 新增承保车辆（增加或者导入 需要更新到期时间）
        //     * @param vehicleInsuranceDto
        //     * @return
        //     */
        //    @PostMapping("/create")
        //    public BaseResult create(@RequestBody @Validated({InsertGroup.class}) VehicleInsuranceDTO vehicleInsuranceDto) {
        //        vehicleInsuranceService.insertSelective(vehicleInsuranceDto);
        //        return BaseResult.success();
        //    }

        //    /**
        //     * 承保车辆信息修改
        //     * @param vehicleInsuranceDto
        //     * @return
        //     */
        //    @PostMapping("/update")
        //    public BaseResult update(@RequestBody @Validated({UpdateGroup.class}) VehicleInsuranceDTO vehicleInsuranceDto) {
        //        vehicleInsuranceService.updateSelective(vehicleInsuranceDto);
        //        return BaseResult.success();
        //    }
    }