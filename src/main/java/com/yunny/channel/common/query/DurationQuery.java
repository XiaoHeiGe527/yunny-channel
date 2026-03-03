package com.yunny.channel.common.query;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Mr. Du
 * @explain 时长订单
 * @createTime 2020/1/3 11:19
 * @motto The more learn, the more found his ignorance.
 */

@Data
public class DurationQuery implements Serializable {
    /**
     * 串号
     */

    private String deviceNo;

    /**
     * 手工订单号
     */
    private String manualOrderNo;

    /**
     * 订单状态
     */
    private int state;

    /**
     * 根据端确定渠道
     */
    private Integer channelNo;

    private String ip;
}

