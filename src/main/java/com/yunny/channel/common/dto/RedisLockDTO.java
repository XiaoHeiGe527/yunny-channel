package com.yunny.channel.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisLockDTO {
    @NotNull(message = "key不能为空")
    private String key;

    @NotNull(message = "value不能为空")
    private String value;

    private Integer expireTime = 0;
}
