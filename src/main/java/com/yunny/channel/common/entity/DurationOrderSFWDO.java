package com.yunny.channel.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DurationOrderSFWDO {

    private String orderNo;

    private String mobile;

    private String userNo;

    private LocalDateTime dueTime;


}
