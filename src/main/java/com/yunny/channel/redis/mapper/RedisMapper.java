package com.yunny.channel.redis.mapper;


import com.yunny.channel.common.result.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author Mr. Du
 * @explain
 * @createTime 2019/12/27 14:25
 * @motto The more learn, the more found his ignorance.
 */
@Component
@Slf4j
public class RedisMapper {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    ValueOperations<String, Object> valueOperations;

    private static final Long SUCCESS = 1L;

    public long incr(String key, long i) {

        long l;
        if (!redisTemplate.hasKey(key)) {
            l = valueOperations.increment(key, i);
            redisTemplate.expire(key, 1, TimeUnit.DAYS);
            return l;
        }
        l = valueOperations.increment(key, i);
        return l;
    }

    /**
     * @param key
     * @param i
     * @return
     */
    public long incrBy31Day(String key, long i) {

        long l;
        if (!redisTemplate.hasKey(key)) {
            l = valueOperations.increment(key, i);
            redisTemplate.expire(key, 31, TimeUnit.DAYS);
            return l;
        }
        l = valueOperations.increment(key, i);
        return l;
    }


    public void addToken(String key, String value) {
        valueOperations.set(key, value, 30L, TimeUnit.DAYS);
    }

    public boolean delToken(String key) {
        return redisTemplate.delete(key);
    }

    public boolean hashExist(String key) {
        return redisTemplate.hasKey(key);
    }

    public boolean mobileLimit(String key, String value, long timeout, TimeUnit timeUnit) {
        //
        return valueOperations.setIfAbsent(key, value, timeout, timeUnit);
    }

    public String userNoGet(String key) {
        Object object = valueOperations.get(key);
        if (object == null) {
            return null;
        }
        return object.toString();
    }

    /**
     * 向redis添加键为k的值v的一条数据
     * 如果已有k则会修改从l开始的v的值
     * 过期时间单位是小时
     *
     * @param key
     * @param value
     * @param time
     */
    public void setKeyAndValueAndExpireTime(String key, String value, long time) {
        valueOperations.set(key, value, time, TimeUnit.HOURS);
    }

    /**
     * 根据字符串KEY从redis 里获取字符串
     *
     * @param key
     * @return
     */
    public String getStringKey(String key) {
        return valueOperations.get(key) == null ? "" : valueOperations.get(key).toString();
    }

    /**
     * 单机获取锁
     *
     * @param key
     * @param value
     * @param expireTime：单位-秒
     * @return
     */
    public boolean getLock(String key, String value, int expireTime) {
        boolean ret = false;
        String script = "if redis.call('setNx',KEYS[1],ARGV[1]) == 1 then if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('expire',KEYS[1],ARGV[2]) else return 0 end end";
        RedisScript<String> redisScript = RedisScript.of(script, Long.class);
        Object result = redisTemplate.execute(redisScript, Collections.singletonList(key), value, expireTime);
        if (SUCCESS.equals(result)) {
            ret = true;
        }
        return ret;
    }

    /**
     * 单机释放锁
     *
     * @param key
     * @param value
     * @return
     */
    public boolean releaseLock(String key, String value) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        RedisScript<String> redisScript = RedisScript.of(script, Long.class);
        Object result = redisTemplate.execute(redisScript, Collections.singletonList(key), value);
        if (SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }



}

