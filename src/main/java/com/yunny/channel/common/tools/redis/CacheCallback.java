package com.yunny.channel.common.tools.redis;

/**
 * 时间：2023-02-21
 * 描述：缓存回调
 **/
public interface CacheCallback<T> {

    /**
     * 执行
     * @return
     */
    T execute();
}
