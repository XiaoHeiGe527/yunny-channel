package com.yunny.channel.redis.service.impl;


import com.yunny.channel.common.dto.RedisLockDTO;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.redis.mapper.RedisMapper;
import com.yunny.channel.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Mr. Du
 * @explain
 * @createTime 2019/12/28 21:00
 * @motto The more learn, the more found his ignorance.
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisMapper redisMapper;

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
            put("exist", redisMapper.hashExist(key));
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



}

