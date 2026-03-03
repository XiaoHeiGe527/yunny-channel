package com.yunny.channel.common.tools.redis;

import com.yunny.channel.common.dto.RedisLockDTO;
import com.yunny.channel.common.result.BaseResult;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis服务
 *
 * @author LJ
 */
@Validated
public interface RedisService {

    /**
     * 设置缓存
     *
     * @param prefix
     * @param key
     * @param value
     * @param expire 过期时间,小于等于0不过期（秒）
     * @return
     */
    boolean set(@NotNull String prefix, @NotBlank String key, @NotNull Object value, long expire);

    /**
     * 设置缓存
     *
     * @param prefix
     * @param key
     * @param value
     * @param expire 过期时间,小于等于0不过期（秒）
     * @return
     */
    boolean setStr(@NotNull String prefix, @NotBlank String key, @NotNull String value, long expire);

    /**
     * 获取缓存字符串
     *
     * @param prefix
     * @param key
     * @return
     */
    String getStr(String prefix, String key);

    /**
     * 获取缓存Bean
     *
     * @param prefix
     * @param key
     * @param clazz
     * @return
     */
    <T> T getBean(@NotNull String prefix, @NotBlank String key, @NotNull Class<T> clazz);

    /**
     * 获取缓存集合
     *
     * @param prefix
     * @param key
     * @param clazz
     * @return
     */
    <T> List<T> getList(@NotNull String prefix, @NotBlank String key, @NotNull Class<T> clazz);

    /**
     * 判断缓存是否存在
     *
     * @param prefix
     * @param key
     * @return
     */
    boolean exists(@NotNull String prefix, @NotBlank String key);

    /**
     * 删除
     *
     * @param prefix
     * @param key
     */
    void delete(@NotNull String prefix, @NotBlank String key);

    /**
     * 批量删除
     *
     * @param redisKeys
     */
    void delete(@NotNull Set<String> redisKeys);

    /**
     * 按规则批量删除
     *
     * @param pattern
     */
    void deletePattern(@NotBlank String pattern);

    /**
     * 更新缓存失效时长
     *
     * @param prefix
     * @param key
     * @param timeout 失效时长(单位秒)
     * @return
     */
    void expire(@NotNull String prefix, @NotBlank String key, long timeout);

    /**
     * 将键的整数值增加1
     *
     * @param prefix
     * @param key
     * @return
     */
    Long incr(@NotNull String prefix, @NotBlank String key);

    /**
     * 将键的整数值减1
     *
     * @param prefix
     * @param key
     * @return
     */
    Long decr(@NotNull String prefix, @NotBlank String key);
    
    /**
              * 长度限制
     *
     * @param prefix
     * @param key
     * @param start
     * @param end
     * @return
     */
    void trim(String prefix, String key, long start, long end);

    /**
     * 增量递增
     *
     * @param key
     * @param add
     * @return
     */
    BaseResult<Long> incr(String key, long add);

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


    /**
     * 向 Redis 的 Hash 数据结构中添加字段和值
     *
     * @param key   Redis键名
     * @param field Hash字段名
     * @param value 字段值
     */
   void hashPut(String key, String field, Object value);


}
