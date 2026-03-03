package com.yunny.channel.controller;

import com.yunny.channel.common.dto.SystemLogDTO;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.SystemLogVO;
import com.yunny.channel.common.query.SystemLogQuery;
import com.yunny.channel.service.SystemLogService;
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
@RequestMapping("/systemLog")
public class SystemLogController {

    @Resource
    private SystemLogService systemLogService;

    @RequestMapping("/listByPage")
    public BaseResult<CommonPager<SystemLogVO>> listByPage(@RequestBody SystemLogQuery query) {
        query.setPageParameter(new PageParameter(query.getCurrentPage(), query.getPageSize()));
    	CommonPager<SystemLogVO> commonPager = systemLogService.listByPage(query);
    	return BaseResult.success(commonPager);
    }

}
