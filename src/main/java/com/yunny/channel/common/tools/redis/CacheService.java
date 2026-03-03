package com.yunny.channel.common.tools.redis;

import com.yunny.channel.common.enums.EnumConstants;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 时间：2023-02-21
 * 描述：缓存接口
 **/
public interface CacheService {
    /**
     * 删除缓存
     *
     * @param key
     */
    boolean deleteCache(String key);

    /**
     * 通过key的前缀删除缓存
     *
     * @param keyPrefix
     * @return
     */
    boolean deleteCacheByPrefix(String keyPrefix);

    /**
     * 设置缓存
     *
     * @param type
     * @param key
     * @param value
     * @param timeout
     * @param timeUnit
     */
    void setCache(EnumConstants.RedisDataType type, String key, Object value, long timeout, TimeUnit timeUnit);

    /**
     * 获取缓存
     *
     * @param type
     * @param key
     * @return
     */
    <R> R getCache(EnumConstants.RedisDataType type, String key);

    /**
     * 获取缓存
     *
     * @param type
     * @param key
     * @param callback
     * @return
     */
    <K, V, R> R getCache(EnumConstants.RedisDataType type, K key, Callback<K, V> callback);


    /**
     * 获取缓存
     *
     * @param type
     * @param key
     * @param callback
     * @param timeout
     * @param timeUnit
     * @param <K>
     * @param <V>
     * @param <R>
     * @return
     */
    <K, V, R> R getCache(EnumConstants.RedisDataType type, K key, Callback<K, V> callback, long timeout, TimeUnit timeUnit);


    /**
     * redis缓存List（默认5分钟）
     *
     * @param redisKey
     * @param callback
     * @param clazz
     * @param <T>
     * @return
     */
    <T> List<T> getCacheList(String redisKey, CacheCallback<List<T>> callback, Class<T> clazz);

    /**
     * 获取redis缓存List
     *
     * @param redisKey
     * @param callback
     * @param timeoutMinute (分钟)
     * @param clazz
     * @param <T>
     * @return
     */
    <T> List<T> getCacheList(String redisKey, CacheCallback<List<T>> callback, long timeoutMinute, Class<T> clazz);


    /**
     * 获取redis缓存List
     *
     * @param redisKey
     * @param callback
     * @param timeout
     * @param timeUnit
     * @param clazz
     * @param <T>
     * @return
     */
    <T> List<T> getCacheList(String redisKey, CacheCallback<List<T>> callback, long timeout, TimeUnit timeUnit, Class<T> clazz);

    /**
     * redis缓存（默认5分钟）
     *
     * @param redisKey
     * @param callback
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T getCache(String redisKey, CacheCallback<T> callback, Class<T> clazz);

    /**
     * 获取redis缓存
     *
     * @param redisKey
     * @param callback
     * @param timeoutMinute (分钟)
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T getCache(String redisKey, CacheCallback<T> callback, long timeoutMinute, Class<T> clazz);

    /**
     * 获取redis缓存
     *
     * @param redisKey
     * @param callback
     * @param timeout
     * @param timeUnit
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T getCache(String redisKey, CacheCallback<T> callback, long timeout, TimeUnit timeUnit, Class<T> clazz);

}
