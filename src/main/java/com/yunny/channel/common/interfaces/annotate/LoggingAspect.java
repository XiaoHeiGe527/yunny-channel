package com.yunny.channel.common.interfaces.annotate;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @program: zijinchakong
 * @description:
 * @author: sunfuwei
 * @create: 2024/07/03
 */
@Slf4j
@Aspect
@Component
public class LoggingAspect {


    @Around("@annotation(logExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint, LogExecutionTime logExecutionTime) throws Throwable {



        // 打印传入的参数值

        log.info("获得注解里的value值.===【{}】..",logExecutionTime.value());

        long startTime = System.currentTimeMillis();

        log.info("开始进入注解...");

        // 执行目标方法
        Object result = joinPoint.proceed();

        log.info("定义result======【{}】",result);



        long endTime = System.currentTimeMillis();
        System.out.println(
                "方法结束了执行时间:\n"+joinPoint.getSignature() + " executed in " + (endTime - startTime) + "ms");

        return result;
    }


}
