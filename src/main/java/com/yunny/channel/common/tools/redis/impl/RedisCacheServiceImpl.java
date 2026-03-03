package com.yunny.channel.common.tools.redis.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.yunny.channel.common.enums.EnumConstants;
import com.yunny.channel.common.exception.MyException;
import com.yunny.channel.common.tools.redis.CacheCallback;
import com.yunny.channel.common.tools.redis.CacheService;
import com.yunny.channel.common.tools.redis.Callback;
import com.yunny.channel.common.tools.redis.RedisUtils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * 时间：2023-02-21
 * 描述：redis缓存实现
 **/
@Slf4j
public class RedisCacheServiceImpl implements CacheService {
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    @Qualifier("redisUtils")
    private RedisUtils redisUtils;

    @Override
    public boolean deleteCache(String key) {
        boolean result = redisTemplate.delete(key);
        if (result) {
            log.info("删除redis缓存key：{}", key);
        }
        return result;
    }

    /**
     * 通过key的前缀删除缓存
     *
     * @param keyPrefix
     * @return
     */
    @Override
    public boolean deleteCacheByPrefix(String keyPrefix) {
        if (!keyPrefix.endsWith("*")) {
            keyPrefix = keyPrefix + "*";
        }
        String finalKeyPrefix = keyPrefix;
        // 新开一个线程去删
        new Thread(() -> {
            log.info("新开一个线程后台删缓存key={}", finalKeyPrefix);
            Set<String> keys = redisUtils.scanKey(finalKeyPrefix);
            if (CollectionUtil.isNotEmpty(keys)) {
                for (String key : keys) {
                    deleteCache(key);
                }
            }
        }).start();
        return true;
    }

    @Override
    public void setCache(EnumConstants.RedisDataType type, String key, Object value, long timeout, TimeUnit timeUnit) {
        switch (type) {
            case STRING:
                deleteCache(key);
                redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
                break;
            case LIST:
                if (value instanceof List) {
                    deleteCache(key);
                    for (Object v : (Collection) value) {
                        redisTemplate.opsForList().leftPush(key, v);
                    }
                    redisTemplate.expire(key, timeout, timeUnit);
                }
                break;
            case SET:
                if (value instanceof Set) {
                    deleteCache(key);
                    for (Object v : (Collection) value) {
                        redisTemplate.opsForSet().add(key, v);
                    }
                    redisTemplate.expire(key, timeout, timeUnit);
                }
                break;
            case HASH:
                if (value instanceof Map) {
                    deleteCache(key);
                    Map data = (Map) value;
                    //从Map中移除掉value为null的键值对
                    data.entrySet().removeIf(entry -> null == ((Map.Entry) entry).getValue());
                    if (CollectionUtil.isNotEmpty(data)) {
                        redisTemplate.opsForHash().putAll(key, data);
                        redisTemplate.expire(key, timeout, timeUnit);
                    }
                }
                break;
            default:
                throw new MyException(String.format("不支持的缓存数据类型：%s 只支持：[string,list,set,hash]", type.getValue()));
        }
    }

    /**
     * 读取缓存，如果缓存没有，就去通过回调方式获取
     *
     * @param type
     * @param key
     * @param callback
     * @param timeout
     * @param timeUnit
     * @return
     */
    private <K, V, R> R doGetCache(EnumConstants.RedisDataType type, K key, Callback<K, V> callback, long timeout, TimeUnit timeUnit) {
        Object value = null;
        try {
            //这里实现先从缓存拿，缓存中没有再执行回调方法拿结果
            value = getCache(type, key.toString());
            if (null == value || ObjectUtils.isEmpty(value)) {
                value = callback.execute(key);
                if (null != value && !ObjectUtils.isEmpty(value)) {
                    setCache(type, key.toString(), value, timeout, timeUnit);
                }
            }
        } catch (Exception e) {
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return (R) value;
    }

    @Override
    public <R> R getCache(EnumConstants.RedisDataType type, String key) {
        switch (type) {
            case STRING:
                return (R) redisTemplate.opsForValue().get(key);
            case LIST:
                return (R) redisTemplate.opsForList().leftPop(key);
            case SET:
                return (R) redisTemplate.opsForSet().members(key);
            case HASH:
                return (R) redisTemplate.opsForHash().entries(key);
            default:
                throw new MyException(String.format("不支持的缓存数据类型：%s 只支持：[string,list,set,hash]", type.getValue()));
        }
    }

    @Override
    public <K, V, R> R getCache(EnumConstants.RedisDataType type, K key, Callback<K, V> callback) {
        return doGetCache(type, key, callback, 24, TimeUnit.HOURS);
    }

    @Override
    public <K, V, R> R getCache(EnumConstants.RedisDataType type, K key, Callback<K, V> callback, long timeout, TimeUnit timeUnit) {
        return doGetCache(type, key, callback, timeout, timeUnit);
    }

    /**
     * redis缓存（默认5分钟）
     *
     * @param redisKey
     * @param callback
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> getCacheList(String redisKey, CacheCallback<List<T>> callback, Class<T> clazz) {
        long timeoutMinute = 5L;
        return getCacheList(redisKey, callback, timeoutMinute, clazz);
    }

    @Override
    public <T> List<T> getCacheList(String redisKey, CacheCallback<List<T>> callback, long timeoutMinute, Class<T> clazz) {
        return getCacheList(redisKey, callback, timeoutMinute, TimeUnit.MINUTES, clazz);
    }

    /**
     * 获取redis缓存List对象
     *
     * @param redisKey
     * @param callback
     * @param timeout
     * @param timeUnit
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> getCacheList(String redisKey, CacheCallback<List<T>> callback, long timeout, TimeUnit timeUnit, Class<T> clazz) {
        String value = (String) redisTemplate.opsForValue().get(redisKey);
        List<T> result = null;
        if (StringUtils.isBlank(value)) {
            result = callback.execute();
            if (result != null) {
                redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(result), timeout, timeUnit);
            }
        } else {
            result = JSON.parseArray(value, clazz);
        }
        return result;
    }


    /**
     * redis缓存
     *
     * @param <T>
     * @param redisKey
     * @param callback
     * @param clazz
     * @return
     */
    public <T> T getCache(String redisKey, CacheCallback<T> callback, Class<T> clazz) {
        long timeoutMinute = 5L;
        return getCache(redisKey, callback, timeoutMinute, clazz);
    }

    /**
     * redis缓存
     *
     * @param redisKey
     * @param callback
     * @param timeoutMinute
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getCache(String redisKey, CacheCallback<T> callback, long timeoutMinute, Class<T> clazz) {
        return getCache(redisKey, callback, timeoutMinute, TimeUnit.MINUTES, clazz);
    }

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
    public <T> T getCache(String redisKey, CacheCallback<T> callback, long timeout, TimeUnit timeUnit, Class<T> clazz) {
        String value = (String) redisTemplate.opsForValue().get(redisKey);
        T result = null;
        boolean isStr = (String.class == clazz);
        if (StringUtils.isBlank(value)) {
            result = callback.execute();
            if (result != null) {
                if (isStr) {
                    redisTemplate.opsForValue().set(redisKey, result, timeout, timeUnit);
                } else {
                    redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(result), timeout, timeUnit);
                }
            }
        } else {
            if (isStr) {
                result = (T) value;
            } else {
                result = JSON.parseObject(value, clazz);
            }
        }
        return result;
    }

    /***
     * 设置缓存
     * @param redisKey
     * @param content
     * @param timeout
     * @param timeUnit
     */
    public void set(String redisKey,String content,Long timeout,TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(redisKey, content, timeout, timeUnit);
    }
}
