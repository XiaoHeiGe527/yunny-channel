package com.yunny.channel.common.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayResponseVO implements Serializable {

    /** 支付宝h5返回 */
    private String body;

    /** 支付宝、微信pc端二维码地址  微信h5地址 */
    private String url;

    /** 支微信appId */
    private String appId;

    /** 时间戳 */
    private String timeStamp;

    /** 随机数 */
    private String nonceStr;

    /** package java不允许所以加上str */
    private String packageStr;

    /** 签名类型 */
    private String signType;

    /** 签名 */
    private String paySign;

    /** 订单号 */
    private String crystalOrderNo;
}