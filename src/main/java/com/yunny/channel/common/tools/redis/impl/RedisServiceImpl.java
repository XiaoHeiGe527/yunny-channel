package com.yunny.channel.common.tools.redis.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

import com.google.common.collect.Maps;
import com.yunny.channel.common.constant.RedisKeyNameConstants;
import com.yunny.channel.common.dto.RedisLockDTO;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.tools.redis.RedisService;
import com.yunny.channel.common.tools.redis.mapper.RedisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Autowired
    private RedisMapper redisMapper;


    @Override
    public boolean set(String prefix, String key, Object value, long expire) {
        return setStr(prefix, key, JSONUtil.toJsonStr(value), expire);
    }

    @Override
    public boolean setStr(String prefix, String key, String value, long expire) {
        String redisKey = StrUtil.builder(prefix, ":", key).toString();
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set(redisKey, value);
        if (expire > 0) {
            return redisTemplate.expire(redisKey, expire, TimeUnit.SECONDS);
        }
        return true;
    }

    @Override
    public boolean exists(String prefix, String key) {
        String redisKey = StrUtil.builder(prefix, ":", key).toString();
        return redisTemplate.hasKey(redisKey);
    }

    @Override
    public void delete(String prefix, String key) {
        String redisKey = StrUtil.builder(prefix, ":", key).toString();
        if (redisTemplate.hasKey(redisKey)) {
            redisTemplate.delete(redisKey);
        }
    }

    @Override
    public void delete(Set<String> keys) {
        if (CollUtil.isNotEmpty(keys)) {
            redisTemplate.delete(keys);
        }
    }

    @Override
    public void deletePattern(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        if (CollUtil.isNotEmpty(keys)) {
            redisTemplate.delete(keys);
        }
    }

    @Override
    public String getStr(String prefix, String key) {
        String redisKey = StrUtil.builder(prefix, ":", key).toString();
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        return operations.get(redisKey);
    }

    @Override
    public <T> T getBean(String prefix, String key, Class<T> clazz) {
        String value = this.getStr(prefix, key);
        return StrUtil.isBlank(value) ? null : JSONUtil.toBean(value, clazz);
    }

    @Override
    public <T> List<T> getList(String prefix, String key, Class<T> clazz) {
        String value = this.getStr(prefix, key);
        return StrUtil.isBlank(value) ? null : JSONUtil.toList(new JSONArray(value), clazz);
    }

    @Async
    @Override
    public void expire(String prefix, String key, long timeout) {
        String redisKey = StrUtil.builder(prefix, ":", key).toString();
        redisTemplate.expire(redisKey, timeout, TimeUnit.SECONDS);
    }

    @Override
    public Long incr(String prefix, String key) {
        String redisKey = StrUtil.builder(prefix, ":", key).toString();
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        return operations.increment(redisKey);
    }

    @Override
    public Long decr(String prefix, String key) {
        String redisKey = StrUtil.builder(prefix, ":", key).toString();
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        return operations.decrement(redisKey);
    }
    
    @Override
    public void trim(String prefix, String key,long start,long end) {
    	String redisKey = StrUtil.builder(prefix, ":", key).toString();
    	redisTemplate.opsForList().trim(redisKey, start, end);
    }


    @Override
    public BaseResult<Long> incr(String key, long i) {
        return BaseResult.success(redisMapper.incr(key, i));
    }

    @Override
    public BaseResult<Long> incrBy31Day(String key, long add) {
        return BaseResult.success(redisMapper.incrBy31Day(key, add));
    }

    @Override
    public void addToken(String key, String value) {
        redisMapper.addToken(key, value);
    }

    @Override
    public BaseResult<Boolean> delToken(String key) {
        return BaseResult.success(redisMapper.delToken(key));
    }

    @Override
    public BaseResult<Map<String, Boolean>> hashExist(String key) {
        return BaseResult.success(new HashMap<String, Boolean>(4) {{
            put(RedisKeyNameConstants.EXIST, redisMapper.hashExist(key));
        }});
    }

    @Override
    public BaseResult<Map<String, Boolean>> mobileLimit(String key, String value, long timeout, TimeUnit timeUnit) {
        return BaseResult.success(new HashMap<String, Boolean>(4) {{
            put("limit", redisMapper.mobileLimit(key, value, timeout, timeUnit));
        }});
    }

    @Override
    public BaseResult<Map<String, String>> userNoGet(String key) {
        return BaseResult.success(new HashMap<String, String>(4) {{
            put("value", redisMapper.userNoGet(key));
        }});

    }

    @Override
    public BaseResult<Boolean> setKeyAndValueAndExpireTime(String key, String value, Long expireTime) {
        redisMapper.setKeyAndValueAndExpireTime(key, value, expireTime);
        return BaseResult.success(true);
    }

    @Override
    public String getStringKey(String key) {
        return redisMapper.getStringKey(key);
    }

    /**
     * @param redisLockDTO
     * @return
     */
    @Override
    public BaseResult<Map<String, Boolean>> getLock(RedisLockDTO redisLockDTO) {
        Map map = Maps.newHashMapWithExpectedSize(1);
        map.put("lock", redisMapper.getLock(redisLockDTO.getKey(), redisLockDTO.getValue(), redisLockDTO.getExpireTime()));
        return BaseResult.success(map);
    }

    /**
     * @param redisLockDTO
     * @return
     */
    @Override
    public BaseResult<Map<String, Boolean>> releaseLock(RedisLockDTO redisLockDTO) {
        Map map = Maps.newHashMapWithExpectedSize(1);
        map.put("releaseLock", redisMapper.releaseLock(redisLockDTO.getKey(), redisLockDTO.getValue()));
        return BaseResult.success(map);
    }

    /**
     * 向 Redis 的 Hash 数据结构中添加字段和值
     *
     * @param key   Redis键名
     * @param field Hash字段名
     * @param value 字段值
     */
    @Override
    public void hashPut(String key, String field, Object value) {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        hashOps.put(key, field, JSONUtil.toJsonStr(value));
    }
}
