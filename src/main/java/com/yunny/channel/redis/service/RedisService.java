package com.yunny.channel.redis.service;



import com.yunny.channel.common.dto.RedisLockDTO;
import com.yunny.channel.common.result.BaseResult;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Mr. Du
 * @explain
 * @createTime 2019/12/28 20:58
 * @motto If you would have leisure, do not waste it.
 */

public interface RedisService {

    /**
     * 增量递增
     *
     * @param key
     * @param add
     * @return
     */
    BaseResult incr(String key, long add);

    BaseResult incrBy31Day(String key, long add);

    /**
     * 存储id
     *
     * @param key
     * @param value
     * @return
     */
    void addToken(String key, String value);

    /**
     * 删除token
     *
     * @param key
     * @return
     */
    BaseResult delToken(String key);

    /**
     * key是否存在
     *
     * @param key
     * @return
     */
    BaseResult<Map<String, Boolean>> hashExist(String key);

    /**
     * 限流
     *
     * @param key
     * @param value
     * @return
     */
    BaseResult<Map<String, Boolean>> mobileLimit(String key, String value, long timeout, TimeUnit timeUnit);

    /**
     * 用户号获取
     *
     * @param key
     * @return
     */
    BaseResult<Map<String, String>> userNoGet(String key);

    /**
     * 存入字符串 并设置过期时间 单位小时
     *
     * @param key
     * @param value
     * @param expireTime
     */
    BaseResult setKeyAndValueAndExpireTime(String key, String value, Long expireTime);

    /**
     * 根据字符串KEY从redis 里获取字符串
     *
     * @param key
     * @return
     */
    String getStringKey(String key);

    /**
     * 单机获取redis锁
     *
     * @param redisLockDTO
     * @return
     */
    BaseResult getLock(RedisLockDTO redisLockDTO);

    /**
     * 单机释放redis锁
     *
     * @param redisLockDTO
     * @return
     */
    BaseResult releaseLock(RedisLockDTO redisLockDTO);


}
