package com.yunny.channel.common.tools.aspect.lock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据缓存
 *
 * @author LIJIAN
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisLock {

    /**
     * 分布式锁的key,默认类名+方法名
     */
    String localKey() default "";

    /**
     * 锁释放时间 默认3600秒
     *
     */
    int leaseTime() default 3600;

    /**
     * 最多等待时间 默认0秒不等待
     *
     */
    int waitTime() default 0;


}
