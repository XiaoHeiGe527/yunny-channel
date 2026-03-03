package com.yunny.channel.job.jobhandler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.yunny.channel.common.constant.ExceptionConstants;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.service.VehiclesOutwardCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @ClassName vehicleCarJob
 * @Description TODO
 * @Author sunfuwei521@qq.com
 * @Date 2025/5/4 7:54
 */
@Component
@Slf4j
public class vehicleCarJob {


    @Resource
    VehiclesOutwardCardService vehiclesOutwardCardService;

    /**
     * 把过期的卡设置为过期
     */
    @XxlJob("handleExpireCardJob")
    public ReturnT<String> handleExpireCardJob() {
        String param = XxlJobHelper.getJobParam();
        log.info("执行任务，时间: [{}]", LocalDateTime.now());
        log.info("任务参数: [{}]",param);
        log.info("开始执行业务代码");
        BaseResult result = vehiclesOutwardCardService.handleExpireCard();
        XxlJobHelper.log("执行完毕");
        if(ExceptionConstants.RESULT_CODE_SUCCESS == result.getCode()){
            log.info("用执行时间时间:[{}],处理数据返回值:[{}]",param,result.getData());
            return ReturnT.SUCCESS;
        }else {
            log.error("报错啦！执行时间时间:[{}],处理数据条数:[{}]",param,result.getData());
            return ReturnT.FAIL;
        }
    }
}
