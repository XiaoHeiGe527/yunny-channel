package com.yunny.channel.common.tools.aspect.cache;

import com.yunny.channel.common.tools.redis.RedisService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

@Aspect
@Component
public class CacheAspect {

    @Autowired
    private RedisService redisService;

    @Pointcut("@annotation(com.yunny.channel.common.tools.aspect.cache.Cache)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        Object object = null;

        String methodName = joinPoint.getSignature().getName();
        Class<?> classTarget = joinPoint.getTarget().getClass();
        Class<?>[] par = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        Method method = classTarget.getMethod(methodName, par);

        Cache cache = method.getAnnotation(Cache.class);
        Class<?> returnType = method.getReturnType();
        String cacheRedisPrefix = "cache:data";
        if (returnType.isAssignableFrom(List.class)) {
            object = redisService.getList(cacheRedisPrefix, cache.key(), returnType);
        } else {
            object = redisService.getBean(cacheRedisPrefix, cache.key(), returnType);
        }
        if (object == null) {
            object = joinPoint.proceed();
            redisService.set(cacheRedisPrefix, cache.key(), object, cache.period());
        }
        return object;
    }

}
