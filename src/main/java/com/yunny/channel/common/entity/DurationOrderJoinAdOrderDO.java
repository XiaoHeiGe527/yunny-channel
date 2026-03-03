package com.yunny.channel.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DurationOrderJoinAdOrderDO {

    private String machineId;

    private String machineKey;

    private String machineToken;

    private String machineName;

    private LocalDateTime retentionTime;

    private String productNo;

    private String orderNo;

    private String deviceNo;

    private String userNo;

}
