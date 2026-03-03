package com.yunny.channel.common.tools.redis;

import com.yunny.channel.common.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ConvertingCursor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 时间：2023-02-21
 * 描述：redis公用业务类
 **/
@Slf4j
public class RedisUtils {
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    /**
     * 扫描key，limit默认为：1000
     *
     * @param pattern
     * @return
     */
    public Set<String> scanKey(String pattern) {
        return this.scanKey(pattern, 5000);
    }

    /**
     * 扫描key
     *
     * @param pattern
     * @param limit
     * @return
     */
    public Set<String> scanKey(String pattern, Integer limit) {
        long start = System.currentTimeMillis();
        Set<String> keys = new LinkedHashSet<>();
        ScanOptions options = ScanOptions.scanOptions().match(pattern).count(limit).build();
        RedisSerializer<String> keySerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
        Cursor<String> cursor = (Cursor) redisTemplate.executeWithStickyConnection(redisConnection ->
                new ConvertingCursor<>(redisConnection.scan(options), keySerializer::deserialize));
        while (cursor.hasNext()) {
            keys.add(cursor.next());
        }
        try {
            cursor.close();
        } catch (Exception e) {
            log.warn("关闭redis游标异常：", e);
        }
        long end = System.currentTimeMillis();
        long took = end - start;
        log.info("Redis ScanKey 耗时：{}ms，Pattern={}，Size={}", took, pattern, keys.size());
        return keys;
    }

    /**
     * 通过计数器进行限流
     * @author ChenLei
     * @param key 缓存键
     * @param limitNum 限制大小
     * @param time 限制时长
     * @param unit 限制时长单位
     * @param limitArgs limitArgs[0]=限制次数，limitArgs[1]=重入锁等待时间(毫秒)
     * @return
     */
    public Long limitByIncrement(String key, int limitNum, int time, TimeUnit unit, Integer... limitArgs){
        long limit = redisTemplate.opsForValue().increment(key, 1);
        if(limit <= limitNum){
            redisTemplate.expire(key, time, unit);
        }
        int count = 0;
        int tryTimes = 500;
        int sleepTime = 100;
        if(limitArgs.length > 0){
            tryTimes = limitArgs[0];
        }
        if(limitArgs.length > 1){
            sleepTime = limitArgs[1];
        }
        while(limit > limitNum){
            if(count == tryTimes){
                throw new MyException("尝试获取限流锁失败");
            }
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                log.error("线程休眠失败...");
            }
            Object numObj = redisTemplate.opsForValue().get(key);
            if(numObj == null) {
                limit = redisTemplate.opsForValue().increment(key, 1);
                redisTemplate.expire(key, time, unit);
            }
            count ++;
        }
        return limit;
    }
}
