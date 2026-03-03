package com.yunny.channel.common.tools.aspect.lock;

import cn.hutool.core.util.StrUtil;

import com.yunny.channel.common.tools.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 分布式锁切面
 *
 * @author LJ
 */
@Slf4j
@Aspect
@Component
public class RedisLockAspect {

    @Autowired
    private RedisService redisService;

    @Pointcut("@annotation(com.yunny.channel.common.tools.aspect.lock.RedisLock)")
    public void pointcut() {
    }


    @Around("pointcut()")
    public Object arround(ProceedingJoinPoint joinPoint) throws Throwable {

        RedisLock rlock = getRedisLockInfo(joinPoint);
        String lockKey = this.getLockKey(joinPoint, rlock);
        boolean redisLock = redisService.exists("rlock:", lockKey);//获取锁
        if (redisLock && rlock.waitTime() > 0) {
            Thread.sleep(rlock.waitTime() * 1000L);//程序最多等待锁释放时间（秒）
            redisLock = redisService.exists("rlock:", lockKey);
        }
        if (redisLock){//锁存在时不往下执行任务
            log.info("已有任务正在执行中...");
            return null;
        }

        try {
            redisService.setStr("rlock:", lockKey, "redisLock", rlock.leaseTime());
            return joinPoint.proceed();
        } finally {
            redisService.delete("rlock:", lockKey);
        }
    }

    /**
     * 获取注解信息
     *
     */
    public RedisLock getRedisLockInfo(ProceedingJoinPoint joinPoint) throws NoSuchMethodException, SecurityException {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Class<?> classTarget = joinPoint.getTarget().getClass();
        Class<?>[] par = methodSignature.getParameterTypes();
        String methodName = joinPoint.getSignature().getName();
        Method objMethod = classTarget.getMethod(methodName, par);
        return objMethod.getAnnotation(RedisLock.class);
    }

    /**
     * redis lock key 生成逻辑 这里只是简单生成，如需投入生产使用，可考虑复杂化
     *
     */
    public String getLockKey(ProceedingJoinPoint joinPoint, RedisLock rlockInfo) {

        String className = joinPoint.getTarget().getClass().getSimpleName();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getMethod().getName();

        return StrUtil.builder(className, ".", methodName, "_", rlockInfo.localKey()).toString();
    }
}
