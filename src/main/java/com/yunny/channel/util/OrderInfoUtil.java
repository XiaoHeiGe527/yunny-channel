package com.yunny.channel.util;

import com.yunny.channel.common.tools.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName OrderInfoUtil
 * @Description TODO
 * @Author sunfuwei521@qq.com
 * @Date 2025/11/1 15:07
 */
@Component
@Slf4j
public class OrderInfoUtil {

    @Autowired
    RedisService redisService;

    @Resource
    private SequenceGenerator sequenceGenerator; // 单号生成工具类

    //时长订单编号
    private final String PLAN_NO = "WHJH";

    //时长订单编号
    private final String USER_NO = "su";


    public String getPlanNo() {
        //获得时间字符串（精确到天）
        String localDateString = NumberUtils.formatLocalDateTimeString(LocalDateTime.now());

        // PLAN_NO + localDateString + sequenceGenerator.getDailySequence();
        //  return NumberUtils.createNumber(ORDER_NO_FRIST,localDateString,number,4);

        //redis每天的+1序号不重复
        long number = redisService.incr(PLAN_NO + ":" + localDateString, 1L).getData();
        return PLAN_NO + localDateString + sequenceGenerator.getDailySequence() + number;

    }



    public String getUserNo() {
        //获得时间字符串（精确到天）
        String localDateString = NumberUtils.formatLocalDateTimeString(LocalDateTime.now());

        //redis每天的+1序号不重复
        long number = redisService.incr(USER_NO + ":" + localDateString, 1L).getData();
        return USER_NO + localDateString + sequenceGenerator.getDailySequence() + number;

    }

}
