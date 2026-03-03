package com.yunny.channel.common.tools.aspect.limit;

import cn.hutool.core.util.StrUtil;
import com.yunny.channel.common.exception.MyException;
import com.yunny.channel.common.tools.redis.RedisService;
import com.yunny.channel.common.util.IPUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class LimitAspect {

    @Autowired
    private RedisService redisService;

    @Pointcut("@annotation(com.yunny.channel.common.tools.aspect.limit.Limit)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String methodName = joinPoint.getSignature().getName();
        Class<?> classTarget = joinPoint.getTarget().getClass();
        Class<?>[] par = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        Method method = classTarget.getMethod(methodName, par);

        Limit limit = method.getAnnotation(Limit.class);
        LimitType limitType = limit.limitType();
        String key = limit.key();
        if (StrUtil.isEmpty(key)) {
            if (limitType == LimitType.IP) {
                key = IPUtil.getIpAddr(request);
            } else {
                key = request.getRequestURI().replaceAll("/", "_");
            }
        }

        String limitRedisPrefix = "limit";
        LimitVo limitVo = redisService.getBean(limitRedisPrefix, key, LimitVo.class);
        limitVo = limitVo != null ? limitVo : new LimitVo(0, new Date(), limit.period());
        if (limitVo.getCount() < limit.maxCount()) {
            limitVo.setCount(limitVo.getCount() + 1);
            long expire = limitVo.getExpire() > 0 ? limitVo.getExpire() : limit.period();
            redisService.set(limitRedisPrefix, key, limitVo, expire);
            return joinPoint.proceed();
        } else {
            throw new MyException("访过于频繁请稍后重试");
        }
    }

}
