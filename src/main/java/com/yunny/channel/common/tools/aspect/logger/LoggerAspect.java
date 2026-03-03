package com.yunny.channel.common.tools.aspect.logger;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class LoggerAspect {

    /**
     * 切点PointCut(注解方式切入)
     */
//    @Pointcut("@annotation(com.zijinchakong.common.tools.aspect.logger.Log)")
    public void pointcut() {
    }

    /**
     * 环绕通知
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
//    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        LoggerVo loggerVo = LoggerVo.builder().times(endTime - startTime).ipAddr(request.getRemoteAddr()).method(method.getName())
                .href(request.getRequestURI()).params(LoggerVo.getParams(joinPoint)).result(result).build();
        log.info(method.getName() + ">>>>>> 方法请求日志：   \r\n  {}", JSONUtil.toJsonStr(loggerVo));
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint
     * @param e
     */
//    @AfterThrowing(pointcut = "pointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        LoggerVo loggerVo = LoggerVo.builder().ipAddr(request.getRemoteAddr()).method(method.getName()).href(request.getRequestURI())
                .params(LoggerVo.getParams(joinPoint)).errorMsg(e.getMessage()).build();
        log.error(method.getName() + ">>>>>> 方法请求异常日志：   \r\n  {}", JSONUtil.toJsonStr(loggerVo));
    }

}
