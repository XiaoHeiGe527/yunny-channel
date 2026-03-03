package com.yunny.channel.common.tools.redis;

/**
 * 时间：2023-02-21
 * 描述：缓存回调
 **/
public interface Callback<I, O> {
    /**
     * 执行
     *
     * @param input
     * @return
     */
    O execute(I input);
}
