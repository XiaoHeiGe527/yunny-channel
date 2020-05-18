package com.yunny.channel.util;

import com.yunny.channel.common.constant.ExceptionConstants;
import com.yunny.channel.common.dto.RedisLockDTO;
import com.yunny.channel.common.exception.ServiceException;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class RedisLockUtil {


    @Autowired
    RedisService redisService;

    /**
     * 获取redis锁
     *
     * @return
     */
    public boolean getRedisLock(RedisLockDTO redisLockDTO) {
        BaseResult<Map<String, Boolean>> baseResult = redisService.getLock(redisLockDTO);
        if (ExceptionConstants.RESULT_CODE_SUCCESS == baseResult.getCode()) {
            Map<String, Boolean> map = baseResult.getData();
            boolean lock = map.get("lock");
            if (!lock) {
                log.info("获取redis锁失败,key=[{}]", redisLockDTO.getKey());
                throw new ServiceException("获取redis锁失效");
            }
            return true;
        } else {
            log.info("获取redis锁出现异常,code=[{}],message=[{}]", baseResult.getCode(), baseResult.getMessage());
            throw new ServiceException("获取redis锁出现异常");
        }
    }
}
