package com.yunny.channel.controller;

import com.yunny.channel.common.constant.JumpUrlConstants;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.service.VehicleInsuranceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @program: yunny-channel
 * @description:
 * @author: sunfuwei
 * @create: 2025/03/27
 */
@Validated
@RestController
@Slf4j
public class RootRedirectController {


    @Resource
    private VehicleInsuranceService vehicleInsuranceService;


    /**
     * 登录
     * @return
     */
    @GetMapping("/login")
    public ModelAndView login() {

        return new ModelAndView(JumpUrlConstants.CAR_INSURANCE_LOGIN);
    }


    @GetMapping("/index")
    public ModelAndView index() {

        return new ModelAndView(JumpUrlConstants.CAR_INSURANCE_LOGIN);
    }

    @PostMapping("/handleCard")
    public BaseResult handleCard() {
        return vehicleInsuranceService.handleCard();
    }


    @GetMapping("/renShi/uploadFkb")
    public ModelAndView importVehicleInsuranceExcel(Model model) {
        log.info("========userNo=======[{}]");
        return new ModelAndView("renshi/uploadFkb");
    }



}