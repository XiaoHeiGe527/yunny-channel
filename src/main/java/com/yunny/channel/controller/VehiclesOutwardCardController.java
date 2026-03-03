package com.yunny.channel.controller;

import com.google.gson.Gson;
import com.yunny.channel.common.dto.SystemLogDTO;
import com.yunny.channel.common.dto.VehiclesOutwardCardDTO;
import com.yunny.channel.common.enums.ResultEnum;
import com.yunny.channel.common.enums.SystemLogTypeEnum;
import com.yunny.channel.common.interfaces.InsertGroup;
import com.yunny.channel.common.interfaces.UpdateGroup;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.util.IPUtils;
import com.yunny.channel.common.vo.SystemUserVO;
import com.yunny.channel.common.vo.VehiclesOutwardCardVO;
import com.yunny.channel.common.query.VehiclesOutwardCardQuery;
import com.yunny.channel.service.SystemLogService;
import com.yunny.channel.service.SystemUserService;
import com.yunny.channel.service.VehiclesOutwardCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

/**
 *车辆卡页面
 */
@Slf4j
@RestController
@RequestMapping("/vehiclesOutwardCard")
public class VehiclesOutwardCardController {

    @Resource
    private SystemLogService systemLogService;

    @Resource
    private VehiclesOutwardCardService vehiclesOutwardCardService;


    @Autowired
    private SystemUserService systemUserService;

    /**
     * 车辆开卡
     * @param vehiclesOutwardCardDto
     * @param userNo
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/create")
    public BaseResult create(@RequestBody @Validated({InsertGroup.class}) VehiclesOutwardCardDTO vehiclesOutwardCardDto
            , @RequestAttribute("userNo") String userNo, HttpServletRequest httpServletRequest) {
        vehiclesOutwardCardDto.setUpdateMan(userNo);
         int count = vehiclesOutwardCardService.insertSelective(vehiclesOutwardCardDto);

         if(count==1){
             SystemUserVO systemUserVO = systemUserService.getByUserNo(userNo);
             String ip = IPUtils.getIpAddr(httpServletRequest);
             SystemLogDTO systemLogDTO = SystemLogDTO.builder()
                     .operationType(SystemLogTypeEnum.ADD.getCode())
                     .content(this.toJSON(vehiclesOutwardCardDto))
                     .userNo(userNo)
                     .createTime(LocalDateTime.now())
                     .url("/vehiclesOutwardCard/create")
                     .ip(ip)
                     .operatorName(systemUserVO.getName())
                     .remarks("新增的车卡号:["+vehiclesOutwardCardDto.getCardNo()+"]")
                     .build();
             systemLogService.insertSelective(systemLogDTO);
         }

        return BaseResult.success();
    }

    @RequestMapping("/listByPage")
    public BaseResult<CommonPager<VehiclesOutwardCardVO>> listByPage(@RequestBody VehiclesOutwardCardQuery query) {
        query.setPageParameter(new PageParameter(query.getCurrentPage(), query.getPageSize()));
    	CommonPager<VehiclesOutwardCardVO> commonPager = vehiclesOutwardCardService.listByPage(query);
    	return BaseResult.success(commonPager);
    }

//    /**
//     * 日志JSON  git reset HEAD   … 取消指定提交
//     * @param vehiclesOutwardCardDto
//     * @return
//     */
  private String toJSON(VehiclesOutwardCardDTO vehiclesOutwardCardDto){
      Gson gson = new Gson();
        return gson.toJson(vehiclesOutwardCardDto);
    }
//    @GetMapping("/getById")
//    public BaseResult getById(@RequestParam("id") Long id) {
//        return BaseResult.success(vehiclesOutwardCardService.getById(id));
//    }

    /**
     * 车辆发车（是否考虑预计还车？）
     * @param vehiclesOutwardCardDto
     * @param userNo
     * @return
     */
    @PostMapping("/cardDepart")
    public BaseResult cardDepart(@RequestBody @Validated({UpdateGroup.class}) VehiclesOutwardCardDTO vehiclesOutwardCardDto
            ,@RequestAttribute("userNo") String userNo, HttpServletRequest httpServletRequest) {

        int resultCode =  vehiclesOutwardCardService.cardDepart(vehiclesOutwardCardDto).getCode();
        //日志
        if(resultCode == ResultEnum.SUCCESS.getCode()){
            SystemUserVO systemUserVO = systemUserService.getByUserNo(userNo);
            String ip = IPUtils.getIpAddr(httpServletRequest);
            SystemLogDTO systemLogDTO = SystemLogDTO.builder()
                    .operationType(SystemLogTypeEnum.ADD.getCode())
                    .content(this.toJSON(vehiclesOutwardCardDto))
                    .userNo(userNo)
                    .createTime(LocalDateTime.now())
                    .url("/vehiclesOutwardCard/cardDepart")
                    .ip(ip)
                    .operatorName(systemUserVO.getName())
                    .remarks("发车的车牌号:["+vehiclesOutwardCardDto.getCarNumber()+"],车卡的卡号:["+vehiclesOutwardCardDto.getCardNo()+"]")
                    .build();
            systemLogService.insertSelective(systemLogDTO);

        }
        return BaseResult.success();
    }


}
