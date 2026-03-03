package com.yunny.channel.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DurationOrderDO extends BaseDO{

    private Long id;

    private String orderNo;

    private String deviceNo;

    private String productNo;

    private String userNo;

    private Integer resetTimes;

    private LocalDateTime dueTime;

    private LocalDateTime showTime;

    private LocalDateTime retentionTime;

    private Integer state;

    private Integer channelNo;
}
