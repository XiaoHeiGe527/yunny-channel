package com.yunny.channel.common.tools.aspect.cache;

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
public @interface Cache {

    /**
     * 资源key
     *
     * @return
     */
    String key();

    /**
     * 缓存时间，单位秒
     *
     * @return
     */
    long period();

}
