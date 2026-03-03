package com.yunny.channel.common.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThirdPayDTO implements Serializable {

    /** 项目订单编号 */
    private String orderNo;

    /** 实付钱 */
    private BigDecimal actuallyPay;

    /** ip */
    private String ip;

    /** openId */
    private String openId;
}
