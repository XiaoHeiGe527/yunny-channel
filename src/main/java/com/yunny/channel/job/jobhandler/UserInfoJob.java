package com.yunny.channel.job.jobhandler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.yunny.channel.common.constant.ExceptionConstants;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.service.SystemResourceService;
import com.yunny.channel.service.UserInfoService;
import com.yunny.channel.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Component
@Slf4j
public class UserInfoJob {


    @Resource
    UserInfoService userInfoService;

    @Resource
    SystemResourceService systemResourceService;

    /**
     * 批量将userBaseInfo数据导入usreInfo中的任务
     */
    @XxlJob("insertUserInfoDataJob")
    public ReturnT<String> insertUserInfoData() {
        String param = XxlJobHelper.getJobParam();
        log.info("批量将userBaseInfo数据导入usreInfo中的任务，时间: [{}]",LocalDateTime.now());
        log.info("任务参数: [{}]",param);
        if(!StringUtil.isNumeric(param)){
            log.info("错误的定时任务参数:[{}]",param);
            log.error("错误的定时任务参数:[{}]",param);
            return ReturnT.FAIL;
        }

        if(param==null||StringUtil.isEmpty(param)){
            param = "0";
        }

        log.info("用户信息迁移到userInfo表任务开始执行");
        BaseResult baseResult = userInfoService.insertUserInfoData(Long.valueOf(param));

        XxlJobHelper.log("执行完毕");
        log.info("用户信息迁移到userInfo表任务执行完毕");

        if(ExceptionConstants.RESULT_CODE_SUCCESS == baseResult.getCode()){

            log.info("用户信息迁移到userInfo表任务执行成功,执行时间时间:[{}],处理数据条数:[{}]",param,baseResult.getData());
            return ReturnT.SUCCESS;
        }else {
            log.info("用户信息迁移到userInfo表任务执行成功,执行时间时间:[{}],处理数据条数:[{}]",param,baseResult.getData());
            log.error("用户信息迁移到userInfo表任务执行成功,执行时间时间:[{}],处理数据条数:[{}]",param,baseResult.getData());
            return ReturnT.FAIL;
        }
    }



    /**
     * 测试一下定时任务
     */
    @XxlJob("myTestJobXC")
    public ReturnT<String> myTestJobXC() {
        String param = XxlJobHelper.getJobParam();
        log.info("执行任务，时间: [{}]",LocalDateTime.now());
        log.info("任务参数: [{}]",param);
        if(!StringUtil.isNumeric(param)){
            log.info("错误的定时任务参数:[{}]",param);
            log.error("错误的定时任务参数:[{}]",param);
            return ReturnT.FAIL;
        }

        if(param==null||StringUtil.isEmpty(param)){
            param = "0";
        }

        log.info("开始执行业务代码");
        BaseResult baseResult = BaseResult.success();
        XxlJobHelper.log("执行完毕");
        if(ExceptionConstants.RESULT_CODE_SUCCESS == baseResult.getCode()){

            log.info("用执行时间时间:[{}],处理数据返回值:[{}]",param,baseResult.getData());
            return ReturnT.SUCCESS;
        }else {

            log.error("报错啦！执行时间时间:[{}],处理数据条数:[{}]",param,baseResult.getData());
            return ReturnT.FAIL;
        }
    }



    @XxlJob("dQJob")
    public ReturnT<String> DQJob() {
        String param = XxlJobHelper.getJobParam();
        log.info("执行任务，时间: [{}]",LocalDateTime.now());
        log.info("任务参数: [{}]",param);
        if(!StringUtil.isNumeric(param)){
            log.info("错误的定时任务参数:[{}]",param);
            log.error("错误的定时任务参数:[{}]",param);
            return ReturnT.FAIL;
        }

        if(param==null||StringUtil.isEmpty(param)){
            param = "0";
        }

        log.info("开始执行业务代码");
        BaseResult baseResult =  systemResourceService.resourceDQ();
        XxlJobHelper.log("执行完毕");
        if(ExceptionConstants.RESULT_CODE_SUCCESS == baseResult.getCode()){

            log.info("用执行时间时间:[{}],处理数据返回值:[{}]",param,baseResult.getData());
            return ReturnT.SUCCESS;
        }else {

            log.error("报错啦！执行时间时间:[{}],处理数据条数:[{}]",param,baseResult.getData());
            return ReturnT.FAIL;
        }
    }


}
