package com.yunny.channel.common.tools.aspect.limit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求限流
 *
 * @author LIJIAN
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Limit {

    /**
     * 资源名称，用于描述接口功能
     *
     * @return
     */
    String name() default "";

    /**
     * 资源 key
     *
     * @return
     */
    String key() default "";

    /**
     * 限制时间，单位秒
     *
     * @return
     */
    long period();

    /**
     * 限制访问次数
     *
     * @return
     */
    int maxCount();

    /**
     * 限制类型
     *
     * @return
     */
    LimitType limitType() default LimitType.CUSTOMER;

}
