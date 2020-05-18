package com.yunny.channel.controller;


import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.service.VmOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vm")
@Slf4j
public class VmOperationController {


    @Autowired
    VmOperationService vmOperationService;

    /**
     * 查询虚机库存状态
     * @param vmType
     * @return
     */
    @PostMapping("/queryvmIsExist")
    public BaseResult<Integer> queryvmIsExist(@RequestParam("vmType")  int vmType)  {
        return vmOperationService.queryvmIsExist(vmType);
    }

}
