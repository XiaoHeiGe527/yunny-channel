package com.yunny.channel.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mr. Du
 * @explain
 * @createTime 2020/3/31 14:09
 * @motto The more learn, the more found his ignorance.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DurationOrderDO2 extends DurationOrderDO {

    private String machineKey;

    private String machineName;
}

